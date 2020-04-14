package de.coronavirus.imis.api.dto;


import lombok.*;

import de.coronavirus.imis.config.domain.UserRole;

@Data
public class RegisterUserRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private UserRole userRole;
}
