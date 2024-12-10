package it.uniromatre.breadexchange2_0.test;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.uniromatre.breadexchange2_0.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
@Tag(name = "Test")
public class TestController {

    private final TestService service;


    @PostMapping
    public ResponseEntity<Integer> saveTest(
            @Valid @RequestBody TestRequest request,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.save(request,connectedUser));
    }


    @GetMapping("{test-id}")
    public ResponseEntity<TestResponse> findById(
            @PathVariable("test-id") Integer testId
    ){
        return ResponseEntity.ok(service.findById(testId));
    }


    @GetMapping
    public ResponseEntity<PageResponse<TestResponse>> findAllTest(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllTest(page,size,connectedUser));
    }

    @GetMapping("/owner")
    public ResponseEntity<PageResponse<TestResponse>> findAllTestByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.findAllTestByOwner(page,size,connectedUser));
    }

    // per aggiornare un field nel entity

    // inutile per il momento esempio per toggle

    @PatchMapping("/test-field/{test-id}")
    public ResponseEntity<Integer> updateField(
            @PathVariable("test-id") Integer testId,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(service.updateField(testId,connectedUser));
    }




    @PostMapping(value = "/cover/{test-id}",consumes = "multipart/form-data")
    public ResponseEntity<?> uploadCoverPictures(
            @PathVariable("test-id") Integer testId,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser
    ){
        service.uploadTestCoverPicture(file,connectedUser, testId);
        return ResponseEntity.accepted().build();
    }


}
































