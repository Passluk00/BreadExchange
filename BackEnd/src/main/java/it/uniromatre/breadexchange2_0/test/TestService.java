package it.uniromatre.breadexchange2_0.test;

import it.uniromatre.breadexchange2_0.common.PageResponse;
import it.uniromatre.breadexchange2_0.exception.OperationNotPermittedException;
import it.uniromatre.breadexchange2_0.file.FileStorageService;
import it.uniromatre.breadexchange2_0.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestMapper testMapper;
    private final TestRepository testRepository;
    private final FileStorageService fileStorageService;

    public Integer save(TestRequest request, Authentication connectedUser) {

        User user = ((User) connectedUser.getPrincipal());
        TestClass test = testMapper.toTest(request);
        test.setOwner(user);

        return testRepository.save(test).getId();
    }

    public TestResponse findById(Integer testId) {
        return testRepository.findById(testId)
                .map(testMapper::toTestResponse)
                .orElseThrow(() -> new EntityNotFoundException("Test entity not found with this id: " + testId));
    }


    public PageResponse<TestResponse> findAllTest(int page, int size, Authentication connectedUser) {

        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<TestClass> tests = testRepository.findAllDisplayableTest(pageable, user.getId());       // tutti tranne quelli del user connesso

        List<TestResponse> testResponses = tests.stream()
                .map(testMapper::toTestResponse)
                .toList();

        return new PageResponse<>(
                testResponses,
                tests.getNumber(),
                tests.getSize(),
                tests.getTotalElements(),
                tests.getTotalPages(),
                tests.isFirst(),
                tests.isLast()
        );
    }


    public PageResponse<TestResponse> findAllTestByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<TestClass> tests = testRepository.findAllByOwner(pageable, user.getId());   // trova tutti quelli del owner

        List<TestResponse> testResponses = tests.stream()
                .map(testMapper::toTestResponse)
                .toList();

        return new PageResponse<>(
                testResponses,
                tests.getNumber(),
                tests.getSize(),
                tests.getTotalElements(),
                tests.getTotalPages(),
                tests.isFirst(),
                tests.isLast()
        );

    }

    // todo metodo temporaneo utilizzato solo per capire il funzionamento per aggiornare un valore

    public Integer updateField(Integer testId, Authentication connectedUser) {

        TestClass test = testRepository.findById(testId)
                .orElseThrow(() -> new EntityNotFoundException("No Entity found with the id: "+ testId));

        User user = ((User) connectedUser.getPrincipal());
        if(!Objects.equals(test.getOwner().getId(),user.getId())){
            throw new OperationNotPermittedException("You cannot update test field");
        }
        test.setDescription("Aggiornato");
        testRepository.save(test);
        return testId;
    }



    public void uploadTestCoverPicture(MultipartFile file, Authentication connectedUser, Integer testId) {

        TestClass test = testRepository.findById(testId)
                .orElseThrow(() -> new EntityNotFoundException("No Entity found with the id: "+ testId));

        User user = ((User) connectedUser.getPrincipal());
        var testCover = fileStorageService.saveFile(file, user.getId());

        test.setTest_url(testCover);
        testRepository.save(test);

    }



    public void newItemAndUploadFile(MultipartFile file, Authentication connectedUser, TestRequest request) {

        User user = ((User) connectedUser.getPrincipal());

        TestClass test = testMapper.toTest(request);
        test.setOwner(user);

        var path = fileStorageService.saveFile(file, user.getId());
        test.setTest_url(path);

        testRepository.save(test);

    }




}































