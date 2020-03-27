package de.coronavirus.imis.services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import de.coronavirus.imis.api.dto.AuthRequestDTO;
import de.coronavirus.imis.config.JwtTokenProvider;
import de.coronavirus.imis.config.domain.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtProvider;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    public Optional<String> loginUserCreateToken(AuthRequestDTO dto) {
        var maybeUser = userRepository.findByUsername(dto.getUserName());
        if (maybeUser.isPresent() && checkPassword(dto.getPassword(), maybeUser.get().getPassword())) {
            var roles = maybeUser.get().getRoles().stream().map(el -> el.getRole().toString()).collect(Collectors.toList());
            return Optional.of(jwtProvider.createToken(maybeUser.get().getUsername(), roles));
        }
        return Optional.empty();
    }

    private boolean checkPassword(String toCheck, String passwordFromDB) {
        return encoder.encode(toCheck).equals(passwordFromDB);
    }




}
