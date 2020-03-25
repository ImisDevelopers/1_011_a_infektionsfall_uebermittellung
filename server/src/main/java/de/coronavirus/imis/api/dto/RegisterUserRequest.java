package de.coronavirus.imis.api.dto;


import lombok.Data;

@Data
public class RegisterUserRequest extends InstitutionDTO {
    private String userName;
    private String password;
}
