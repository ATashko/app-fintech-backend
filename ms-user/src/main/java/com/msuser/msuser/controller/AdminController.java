package com.msuser.msuser.controller;


import com.msuser.msuser.dto.UserRegistrationDTO;
import com.msuser.msuser.service.IUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_admin_client_role')")
public class AdminController {

    private final IUserService userService;

    public AdminController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public ResponseEntity<?> findAllUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.ACCEPTED);
    }

    @PostMapping("/create")
    public ResponseEntity<?> findUserByUserName(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) throws URISyntaxException {
        String response = userService.createUser(userRegistrationDTO);
        return ResponseEntity.created(new URI("/keycloak/user/create")).body(response);
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<?> findUserByUserName(@PathVariable @Valid String username) {
        return new ResponseEntity<>(userService.searchUserByUsername(username), HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> findUserByUserName(@PathVariable @NotNull String userId, @RequestBody @Valid UserRegistrationDTO userRegistrationDTO)  {
        userService.updateUser(userId,userRegistrationDTO);
        return ResponseEntity.ok("User successfully updated");
    }


    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable @NotNull String userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
