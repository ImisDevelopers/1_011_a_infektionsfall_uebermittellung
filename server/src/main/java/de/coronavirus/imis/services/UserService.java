package de.coronavirus.imis.services;

import de.coronavirus.imis.api.dto.UserDTO;
import de.coronavirus.imis.config.domain.User;
import de.coronavirus.imis.config.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;

    public List<User> getUsersForCurrentInstitution() {
        return this.userRepository.getAllByInstitution_Id(authService.getCurrentUser().getInstitutionId());
    }

    public User updateInstitutionUser(UserDTO userDTO) {
        final User user = this.userRepository
                .findById(userDTO.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + userDTO.getId() + " does not exist."));
        user.firstName(userDTO.getFirstName());
        user.lastName(userDTO.getLastName());
        user.username(userDTO.getUsername());
        user.userRole(userDTO.getUserRole());
        return this.userRepository.saveAndFlush(user);
    }

    public void deleteInstitutionUser(Long id) {
        this.userRepository.deleteById(id);
    }
}
