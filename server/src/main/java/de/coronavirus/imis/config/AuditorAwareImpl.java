package de.coronavirus.imis.config;

import de.coronavirus.imis.config.domain.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<User> {
	@Override
	public Optional<User> getCurrentAuditor() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if(authentication != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				&& authentication.getPrincipal()!="anonymousUser")
		{
			var user = (User) authentication.getPrincipal();
			Optional<User> opt = Optional.of(user);
			return opt;
		}

		return Optional.empty();
	}
}