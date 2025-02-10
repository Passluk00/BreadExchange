package it.uniromatre.breadexchange2_0.controller;

import it.uniromatre.breadexchange2_0.bakery.BakeryResponse;
import it.uniromatre.breadexchange2_0.bakery.registerRequest.BRRService;
import it.uniromatre.breadexchange2_0.bakery.registerRequest.BakeryRegisterRequest;
import it.uniromatre.breadexchange2_0.bakery.BakeryService;
import it.uniromatre.breadexchange2_0.common.PageResponse;
import it.uniromatre.breadexchange2_0.user.UserResponse;
import it.uniromatre.breadexchange2_0.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class AdminController {

    private final UserService userService;
    private final BakeryService bakeryService;
    private final BRRService brrService;


    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<PageResponse<UserResponse>> getAllUsers(                                                      // Get All Users
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return ResponseEntity.ok(userService.findAllUsers(page,size));
    }


    @PatchMapping("/ban")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<Void> banUser(                                                                                // Ban & UnBan User
        @RequestParam (name = "id") Integer id){
        userService.banUser(id);
        return ResponseEntity.accepted().build();
    }

    @PatchMapping("/banBakery")
    public ResponseEntity<Void> banBakery(
            @RequestParam(name = "idBac") Integer idBac
    ){
        this.bakeryService.banBakery(idBac);
        return ResponseEntity.accepted().build();
    }


    @GetMapping("/test")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> testSicurezza(){
        return ResponseEntity.ok("Hai permessi admin");
    }


    @PostMapping("/enableBakery")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> enableBakery(
            @RequestParam (name = "id") Integer id,
            Authentication connectedUser
            ) throws MessagingException {
        this.bakeryService.acceptRequest(connectedUser, id);
        return ResponseEntity.accepted().build();
    }


    @DeleteMapping("/rejectRequest")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> rejectRequest(
            @RequestParam (name = "id") Integer id,
            Authentication connectedUser
    ) throws MessagingException {
        bakeryService.rejectRequestNew(connectedUser,id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/getAllRequest")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<PageResponse<BakeryRegisterRequest>> getAllRequest(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return ResponseEntity.ok(brrService.getAllRequest(page,size));
    }


    @GetMapping("/searchByName")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<PageResponse<BakeryRegisterRequest>> searchByName(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return ResponseEntity.ok(brrService.search(page,size,name));
    }

    @GetMapping("/getAllBakery")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<PageResponse<BakeryResponse>> getAllBakery(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return ResponseEntity.ok(bakeryService.getAllBakery(page,size));
    }



}