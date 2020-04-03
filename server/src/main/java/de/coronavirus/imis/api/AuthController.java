package de.coronavirus.imis.api;


import de.coronavirus.imis.api.dto.CreateInstitutionDTO;
import de.coronavirus.imis.config.domain.UserRole;
import de.coronavirus.imis.domain.Institution;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register/user")
    public ResponseEntity<User> registerUser(@RequestBody RegisterUserRequest registerUserRequest,
                                             @AuthenticationPrincipal Authentication auth) {
        if (auth != null && isAdmin(auth)) {
            var user = authService.registerUser(registerUserRequest, auth.getName());
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/register/institution")
    public ResponseEntity<Institution> registerInstitution(@RequestBody CreateInstitutionDTO institutionDTO) {
        // TODO add handling for right, has to be decided on
        return ResponseEntity.ok(authService.createInstitution(institutionDTO));
    }

    @GetMapping("/user")
    public ResponseEntity<User> currentUser(@AuthenticationPrincipal Authentication auth) {
        if (auth != null) {
            var user = authService.getUserByUsername(auth.getName());
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/institution")
    public ResponseEntity<Institution> getInstitution(@AuthenticationPrincipal Authentication auth) {
        if (auth != null && isAdmin(auth)) {
            var institution = authService.getInstitutionFromUser(auth.getName());
            return ResponseEntity.ok(institution);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    private boolean isAdmin(Authentication auth) {
        for (var a : auth.getAuthorities()) {
            if (a.getAuthority().equals(UserRole.USER_ROLE_ADMIN.name())) {
                return true;
            }

        }
        return false;
    }
}