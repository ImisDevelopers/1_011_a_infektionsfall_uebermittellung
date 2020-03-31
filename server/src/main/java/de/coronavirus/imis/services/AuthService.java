package de.coronavirus.imis.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import de.coronavirus.imis.api.dto.AuthRequestDTO;
import de.coronavirus.imis.api.dto.RegisterUserRequest;
import de.coronavirus.imis.config.JwtTokenProvider;
import de.coronavirus.imis.config.domain.AuthorityRepository;
import de.coronavirus.imis.config.domain.User;
import de.coronavirus.imis.config.domain.UserAlreadyExistsException;
import de.coronavirus.imis.config.domain.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtProvider;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final InstitutionService institutionService;

    public Optional<String> loginUserCreateToken(AuthRequestDTO dto) {
        var maybeUser = userRepository.findByUsername(dto.getUserName());
        if (maybeUser.isPresent() && checkPassword(dto.getPassword(), maybeUser.get().getPassword())) {
            var roles = maybeUser.get().roles().stream().map(el -> el.getRole().toString()).collect(Collectors.toList());
            return Optional.of(jwtProvider.createToken(maybeUser.get().getUsername(), roles));
        }
        return Optional.empty();
    }

    private boolean checkPassword(String toCheck, String passwordFromDB) {
        return encoder.matches(toCheck, passwordFromDB);
    }

    @SneakyThrows
    public User registerUser(RegisterUserRequest registerUserRequest) {
        if (userRepository.findByUsername(registerUserRequest.getUserName()).isPresent()) {
            throw new UserAlreadyExistsException("user with name" + registerUserRequest.getUserName() + " already exists");
        }
        var authority = authorityRepository.findFirstByRole(registerUserRequest.getInstitutionType()).orElseThrow();
        var encodedPw = encoder.encode(registerUserRequest.getPassword());
        var user = new User().username(registerUserRequest.getUserName()).password(encodedPw).roles(List.of(authority));
        userRepository.save(user);
        institutionService.createInstiution(registerUserRequest);
        return user;
    }


}
