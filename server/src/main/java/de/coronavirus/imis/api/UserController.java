package de.coronavirus.imis.api;

import de.coronavirus.imis.api.dto.UserDTO;
import de.coronavirus.imis.config.domain.User;
import de.coronavirus.imis.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDTO> getInstitutionUsers() {
        return this.userService.getUsersForCurrentInstitution().stream()
                .map(this::mapUser)
                .collect(Collectors.toList());
    }

    private UserDTO mapUser(User user) {
        final UserDTO userDTO = new UserDTO();
        userDTO.setId(user.id());
        userDTO.setAuthorities(user.getAuthorities());
        userDTO.setFirstName(user.firstName());
        userDTO.setLastName(user.lastName());
        userDTO.setUsername(user.username());
        userDTO.setUserRole(user.userRole());
        return userDTO;
    }

}
