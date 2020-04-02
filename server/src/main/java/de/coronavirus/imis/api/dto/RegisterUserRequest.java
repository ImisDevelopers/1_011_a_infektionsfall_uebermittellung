package de.coronavirus.imis.api.dto;


import lombok.*;

import de.coronavirus.imis.config.domain.UserRole;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterUserRequest extends InstitutionDTO {
    private String userName;
    private String password;
    private UserRole userRole;
}
