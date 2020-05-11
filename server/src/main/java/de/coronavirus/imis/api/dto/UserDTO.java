package de.coronavirus.imis.api.dto;

import de.coronavirus.imis.config.domain.UserRole;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class UserDTO {
	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private UserRole userRole;
	private Collection<? extends GrantedAuthority> authorities;
}
