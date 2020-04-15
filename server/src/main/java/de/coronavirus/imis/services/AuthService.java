package de.coronavirus.imis.services;

import de.coronavirus.imis.api.dto.AuthRequestDTO;
import de.coronavirus.imis.api.dto.ChangePasswordDTO;
import de.coronavirus.imis.api.dto.CreateInstitutionDTO;
import de.coronavirus.imis.api.dto.RegisterUserRequest;
import de.coronavirus.imis.api.exception.ForbiddenException;
import de.coronavirus.imis.config.JwtTokenProvider;
import de.coronavirus.imis.config.domain.User;
import de.coronavirus.imis.config.domain.UserAlreadyExistsException;
import de.coronavirus.imis.config.domain.UserRepository;
import de.coronavirus.imis.domain.Institution;
import de.coronavirus.imis.domain.InstitutionImpl;
import de.coronavirus.imis.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
	private final JwtTokenProvider jwtProvider;
	private final PasswordEncoder encoder;
	private final UserRepository userRepository;
	private final InstitutionService institutionService;

	private final UserMapper userMapper;

	public Optional<String> loginUserCreateToken(AuthRequestDTO dto) {
		var maybeUser = userRepository.findByUsername(dto.getUsername());
		if (maybeUser.isPresent() && checkPassword(dto.getPassword(), maybeUser.get().getPassword())) {
			var role = maybeUser.get().getAuthorities().stream()
					.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
			return Optional.of(jwtProvider.createToken(maybeUser.get().getUsername(), role));
		}
		log.info("Did not find user for username {}", dto.getUsername());
		maybeUser.ifPresent(user -> log.info("check password for username {} returned {}",
				dto.getUsername(), checkPassword(dto.getPassword(), user.getPassword())));
		return Optional.empty();
	}

	private boolean checkPassword(String toCheck, String passwordFromDB) {
		return encoder.matches(toCheck, passwordFromDB);
	}

	@SneakyThrows
	public User registerUser(RegisterUserRequest registerUserRequest, String username) {
		if (userRepository.findByUsername(registerUserRequest.getUsername()).isPresent()) {
			throw new UserAlreadyExistsException("user with name" + registerUserRequest.getUsername() + " already exists");
		}

		var authUser = userRepository.findByUsername(username).get();
		var institution = institutionService.getInstitution(
				authUser.getInstitutionId(),
				authUser.getInstitutionType());

		var user = createUser(registerUserRequest, institution);
		return user;
	}

	private User createUser(RegisterUserRequest userDTO, Institution institution) {
		var user = userMapper.toUser(userDTO)
				.institution((InstitutionImpl) institution);
		user = userRepository.save(user);
		return user;
	}

	public Institution createInstitution(CreateInstitutionDTO createInstitutionDTO) {
		var institution = institutionService.addInstitution(createInstitutionDTO);
		var user = createUser(createInstitutionDTO.getUser(), institution);
		institution = institutionService.getInstitution(user.getInstitutionId(), user.getInstitutionType());
		return institution;
	}

	public void changePassword(ChangePasswordDTO changePasswordDTO) {
		final User user = getCurrentUser();
		if (!this.checkPassword(changePasswordDTO.getOldPassword(), user.getPassword())) {
			throw new ForbiddenException("Passwort inkorrekt");
		}
		var encodedPw = encoder.encode(changePasswordDTO.getNewPassword());
		final var updatedUser = user.toBuilder().password(encodedPw).build();
		userRepository.save(updatedUser);
	}

	public User getCurrentUser() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (User) authentication.getPrincipal();
	}

	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username).get();
	}

	public InstitutionImpl getInstitutionFromUser(String username) {
		var user = getUserByUsername(username);
		return institutionService.getInstitution(user.getInstitutionId(), user.getInstitutionType());
	}


}
