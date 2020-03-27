package de.coronavirus.imis.api;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import de.coronavirus.imis.api.dto.AuthRequestDTO;
import de.coronavirus.imis.api.dto.RegisterUserRequest;
import de.coronavirus.imis.api.dto.TokenDTO;
import de.coronavirus.imis.config.domain.User;
import de.coronavirus.imis.services.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final ResponseEntity<TokenDTO> notAllowed = ResponseEntity.status(HttpStatus.FORBIDDEN).build();


    @PostMapping
    public ResponseEntity<TokenDTO> signInUser(@RequestBody AuthRequestDTO authRequestDTO) {
        return authService.loginUserCreateToken(authRequestDTO)
                .map(TokenDTO::new)
                .map(ResponseEntity::ok)
                .orElse(notAllowed);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        var user = authService.registerUser(registerUserRequest);
        if (user != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


}
