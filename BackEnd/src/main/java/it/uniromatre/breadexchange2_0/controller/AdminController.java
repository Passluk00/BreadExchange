package it.uniromatre.breadexchange2_0.controller;

import it.uniromatre.breadexchange2_0.common.PageResponse;
import it.uniromatre.breadexchange2_0.user.UserResponse;
import it.uniromatre.breadexchange2_0.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class AdminController {

    private final UserService userService;


    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<PageResponse<UserResponse>> getAllUsers(                                                      // Get All Users
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return ResponseEntity.ok(userService.findAllUsers(page,size));
    }

    @PatchMapping("/ban/{user-id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<Void> banUser(                                                                                // Ban & UnBan User
        @PathVariable("user-id") Integer id){
        return ResponseEntity.ok(userService.banUser(id));
    }


    @GetMapping("/test")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<?> testSicurezza(){
        return ResponseEntity.ok("Hai permessi admin");
    }



}