package de.coronavirus.imis.api.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class AuthRequestDTO {
	@NotBlank
	@Email
	private String username;

	@NotBlank
	private String password;

}
