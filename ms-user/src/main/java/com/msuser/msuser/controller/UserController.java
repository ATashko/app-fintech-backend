package com.msuser.msuser.controller;


import com.msuser.msuser.dto.UserRegistrationDTO;
import com.msuser.msuser.service.IUserService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_admin_client_role')")
    @GetMapping("/search")
    public ResponseEntity<?> findAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ROLE_user_client_role') or hasRole('ROLE_admin_client_role')")
    @GetMapping("/search/{username}")
    public ResponseEntity<?> findUserByUserName(@PathVariable @Valid String username) {
        return new ResponseEntity<>(userService.searchUserByUsername(username), HttpStatus.ACCEPTED);
    }



    @PreAuthorize("hasRole('ROLE_user_client_role') or hasRole('ROLE_admin_client_role')")
    @PutMapping("/update/{userId}")
    public ResponseEntity<?> findUserByUserName(@PathVariable @NotNull String userId, @RequestBody @Valid UserRegistrationDTO userRegistrationDTO)  {
        userService.updateUser(userId,userRegistrationDTO);
        return ResponseEntity.ok("User successfully updated");
    }

    @PreAuthorize("hasRole('ROLE_admin_client_role')")
    @DeleteMapping("delete/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable @NotNull String userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
