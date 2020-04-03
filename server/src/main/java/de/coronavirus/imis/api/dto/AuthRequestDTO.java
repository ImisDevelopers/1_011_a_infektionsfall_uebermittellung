package de.coronavirus.imis.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AuthRequestDTO {
    @NotBlank
    @Email
    private String username;

    @NotBlank
    private String password;

}
