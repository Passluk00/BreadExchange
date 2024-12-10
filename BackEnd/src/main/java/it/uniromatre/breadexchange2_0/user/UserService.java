package it.uniromatre.breadexchange2_0.user;

import it.uniromatre.breadexchange2_0.auth.ChangePasswordRequest;
import it.uniromatre.breadexchange2_0.common.PageResponse;
import it.uniromatre.breadexchange2_0.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final FileStorageService fileStorageService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;



    public void uploadProfilePicture(MultipartFile file, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        var picture = fileStorageService.saveFile(file, user.getId());
        user.setUrl_picture(picture);
        userRepository.save(user);
    }


    public PageResponse<UserResponse> findAllUsers(int page, int size) {

        Pageable pageable = PageRequest.of(page,size, Sort.by("username").descending());
        Page<User> users = (userRepository.findAll(pageable));

        List<UserResponse> userResponses = users.stream()
                .map(userMapper::fromUser)
                .toList();

        return new PageResponse<>(
                userResponses,
                users.getNumber(),
                users.getSize(),
                users.getTotalElements(),
                users.getTotalPages(),
                users.isFirst(),
                users.isLast()
        );
    }

    public Void banUser(Integer id) {
        User user = userRepository.findUserById(id);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + id);
        }

        user.setAccountLocked(!user.isAccountLocked());
        userRepository.save(user);
        return null;
    }


    public void changePassword(
            ChangePasswordRequest request,
            Authentication connectedUser
    ){

        var user = (User) connectedUser.getPrincipal();

        // richiesta è vuota perchè??



        log.info("richiesta: " + request.getCurrentPwd());
        log.info("richiesta: " + request.getNewPwd());
        log.info("richiesta: " + request.getConfirmNewPwd());


        log.info("current pwd da request: " + request.getCurrentPwd());     // null  ->   new Password
        log.info("current pwd da user: " + user.getPassword());

        if (!passwordEncoder.matches(request.getCurrentPwd(), user.getPassword())) {
            throw new IllegalStateException("Wrong Password");
        }
        if (!request.getNewPwd().equals(request.getConfirmNewPwd())) {
            throw new IllegalStateException("Password are not the same");
        }
        user.setPassword(passwordEncoder.encode(request.getConfirmNewPwd()));
        userRepository.save(user);
    }
}
























