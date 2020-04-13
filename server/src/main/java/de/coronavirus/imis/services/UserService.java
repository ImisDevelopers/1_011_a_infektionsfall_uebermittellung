package de.coronavirus.imis.services;

import de.coronavirus.imis.config.domain.User;
import de.coronavirus.imis.config.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;

    public List<User> getUsersForCurrentInstitution() {
        return this.userRepository.getAllByInstitution_Id(authService.getCurrentUser().getInstitutionId());
    }

}
