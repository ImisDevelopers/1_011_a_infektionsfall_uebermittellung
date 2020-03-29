package de.coronavirus.imis.api.dto;


import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterUserRequest extends InstitutionDTO {
    private String userName;
    private String password;
}
