package de.coronavirus.imis.mapper;

import de.coronavirus.imis.api.dto.RegisterUserRequest;
import de.coronavirus.imis.api.dto.UserDTO;
import de.coronavirus.imis.config.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.ManyToOne;


@Mapper
public abstract class UserMapper {

	@Autowired
	protected PasswordEncoder pwEncoder;

	@Mapping(target = "password", expression = "java( pwEncoder.encode(dto.getPassword()) )")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "institution", ignore = true)
	@Mapping(target = "authorities", ignore = true)
	public abstract User toUser(RegisterUserRequest dto);

	public abstract UserDTO toUserDTO(User user);
}
