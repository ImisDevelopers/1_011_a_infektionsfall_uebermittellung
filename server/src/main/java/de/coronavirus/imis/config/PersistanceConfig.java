package de.coronavirus.imis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.coronavirus.imis.config.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing (auditorAwareRef = "auditorAware")
class PersistenceConfig {

	@Bean
	public AuditorAware<User> auditorAware() {
		return new AuditorAwareImpl();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}

}