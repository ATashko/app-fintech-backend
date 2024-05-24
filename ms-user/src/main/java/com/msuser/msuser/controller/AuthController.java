package com.msuser.msuser.controller;

import com.msuser.msuser.dto.UserRegistrationDTO;
import com.msuser.msuser.service.IUserService;
import com.msuser.msuser.util.TokenProvider;
import jakarta.validation.Valid;
import lombok.Getter;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.URISyntaxException;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
public class AuthController {

    private final IUserService userService;
    private final TokenProvider tokenProvider;

    public AuthController(IUserService userService, TokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO) throws URISyntaxException {
        String response = userService.createUser(userRegistrationDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) throws IOException {
        UserRepresentation user = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());

        if (user != null) {
            String accessToken = tokenProvider.requestToken(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(accessToken);
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }


    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/home";
    }

    @Getter
    public static class LoginRequest {
        private String username;
        private String password;

        // Getters y setters

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }
}
