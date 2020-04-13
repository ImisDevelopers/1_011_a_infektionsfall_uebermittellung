package de.coronavirus.imis.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import de.coronavirus.imis.api.dto.RegisterUserRequest;
import de.coronavirus.imis.config.domain.User;


@Mapper
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder pwEncoder;

    @Mapping(target = "password", expression = "java( pwEncoder.encode(dto.getPassword()) )")
    public abstract User toUser(RegisterUserRequest dto);
}
