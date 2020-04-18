package de.coronavirus.imis.api;

import de.coronavirus.imis.api.dto.UserDTO;
import de.coronavirus.imis.mapper.UserMapper;
import de.coronavirus.imis.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final UserMapper userMapper;

	@GetMapping
	public List<UserDTO> getInstitutionUsers() {
		return this.userService.getUsersForCurrentInstitution().stream()
				.map(userMapper::toUserDTO)
				.collect(Collectors.toList());
	}

	@PutMapping
	public UserDTO updateInstitutionUser(UserDTO userDTO) {
		log.info("Updating user " + userDTO);
		return this.userMapper.toUserDTO(this.userService.updateInstitutionUser(userDTO));
	}

	@DeleteMapping("/{id}")
	public void deleteInstitutionUser(@PathVariable Long id) {
		log.info("Deleting user " + id);
		this.userService.deleteInstitutionUser(id);
	}

}
