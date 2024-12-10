package it.uniromatre.breadexchange2_0.controller;

import io.swagger.v3.oas.annotations.Parameter;
import it.uniromatre.breadexchange2_0.auth.ChangePasswordRequest;
import it.uniromatre.breadexchange2_0.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {


    private final UserService userService;


    // Funzione per aggiornare la foto del profilo

    @PostMapping(value = "/upload-profile-picture", consumes = "multipart/form-data")
    @PreAuthorize("hasAuthority('customer:update')")
    public ResponseEntity<?> uploadProfilePicture(
            @Parameter()
            @RequestPart("file")MultipartFile file,
            Authentication connectedUser
            ){
        userService.uploadProfilePicture(file,connectedUser);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/changePWD")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Authentication connectedUser
    ){
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok("Password changed successfully!!");
    }


    @GetMapping("/test")
    public ResponseEntity<?> testSicurezza(){
        return ResponseEntity.ok("Hai permessi admin");
    }





}
