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
public class RegistrationController {

    private final IUserService userService;

    public RegistrationController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("registration/create")
    public ResponseEntity<?> create(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) throws URISyntaxException {
        String response = userService.createUser(userRegistrationDTO);
        return ResponseEntity.created(new URI("/keycloak/user/registration/create")).body(response);
    }





}
