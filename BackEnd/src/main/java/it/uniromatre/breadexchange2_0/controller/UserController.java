package it.uniromatre.breadexchange2_0.controller;

import io.swagger.v3.oas.annotations.Parameter;
import it.uniromatre.breadexchange2_0.bakery.registerRequest.BakeryRegisterRequest;
import it.uniromatre.breadexchange2_0.common.PageResponse;
import it.uniromatre.breadexchange2_0.token.tokenVerifyRequest;
import it.uniromatre.breadexchange2_0.token.tokenVerifyResponse;
import it.uniromatre.breadexchange2_0.user.UserFrontEndInfoResponse;
import it.uniromatre.breadexchange2_0.user.UserFrontEndResponse;
import it.uniromatre.breadexchange2_0.user.UserResponse;
import it.uniromatre.breadexchange2_0.user.UserService;
import it.uniromatre.breadexchange2_0.user.address.NewAddress;
import it.uniromatre.breadexchange2_0.email.emailVerifyRequest;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
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
            @Parameter()
            @RequestParam("direction") int direction,
            Authentication connectedUser
            ){
        userService.uploadProfilePicture(file,connectedUser,direction);
        return ResponseEntity.accepted().build();
    }


    @GetMapping("/test")
    public ResponseEntity<?> testSicurezza(){
        return ResponseEntity.ok("Hai permessi user");
    }


    // utilizzabile per cercare qualcuno direttamente con il suo id

    @GetMapping("/{user-id}")
    public ResponseEntity<?> getUser(
            @PathVariable("user-id") Integer userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    // serve per prendere i dati dal suo token

    @GetMapping("/me")
    public ResponseEntity<UserFrontEndResponse> getCurrentUser(
            Authentication connectedUser
    ) {
        if(connectedUser != null){
            return ResponseEntity.ok(userService.getData(connectedUser));
        }
        return ResponseEntity.badRequest().build();
    }


    @GetMapping("/info")
    public ResponseEntity<UserFrontEndInfoResponse> getUserInfo(
            Authentication connectedUser
    ){
        return ResponseEntity.ok(userService.getInfo(connectedUser));
    }


    @PostMapping("/addAddress")
    public ResponseEntity<?> addAddress(
            Authentication connectedUser,
            @RequestBody NewAddress address
    ){
        this.userService.addAddress(connectedUser, address);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/getAddress")
    public ResponseEntity<?> getAddress(
            Authentication connectedUser
    ){
        return ResponseEntity.ok(userService.getAddress(connectedUser));
    }


    @PostMapping("/verificaEmail")
        public ResponseEntity<?> verificaEmail(
                Authentication connectedUser,
                @RequestBody @Valid emailVerifyRequest request
                )throws MessagingException {
        userService.verificaEmail(request, connectedUser);
        return ResponseEntity.ok().build();
        };


    @PostMapping("/verificaToken")
    public ResponseEntity<tokenVerifyResponse> verifyToken(
            Authentication connectedUser,
            @RequestBody @Valid tokenVerifyRequest request
    ){
        return ResponseEntity.ok(userService.verifyToken(request,connectedUser));
    }

    @PostMapping("/sendRequest")
    public ResponseEntity<?> sendRequest(
            @RequestBody @Valid BakeryRegisterRequest request,
            Authentication connectedUser
            ){

        userService.sendRequest(request, connectedUser);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/isAdmin")
    public Boolean isAdmin(
            Authentication connectedUser
    ){
        return userService.isAdmin(connectedUser);
    }


    @GetMapping("/searchUserByName")
    @PreAuthorize("hasAuthority('customer:read')")
    public ResponseEntity<PageResponse<UserResponse>> searchUserByName(                                                 // Get All Users
        @RequestParam(name = "name") String name,
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return ResponseEntity.ok(userService.searchUserByName(page,size,name));
    }








}
