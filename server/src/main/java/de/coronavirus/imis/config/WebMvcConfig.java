package de.coronavirus.imis.config;

import de.coronavirus.imis.config.properties.CorsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {
	public static final String PROPERTY_CORS_ORIGINS = "server.http.cors.allowed-origins";

	@Autowired
	private CorsProperties corsProperties;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		List<String> allowedOrigins = corsProperties.getAllowedOrigins();
		if (allowedOrigins != null && allowedOrigins.size() > 0) {
			// Enable CORS
			var mapping = registry.addMapping(SpringSecurityConfig.API_PREFIX + "/**")
					.allowedOrigins(allowedOrigins.toArray(new String[0]))
					.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
					.allowCredentials(true);

			log.info("Enabled CORS for Origins " + String.join(", ", allowedOrigins));
		} else {
			log.info("CORS disabled");
		}
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// Map "/word", "/word/word", and "/word/word/word" - except for anything starting with "/api/..." or ending with
		// a file extension like ".js" - to index.html. By doing this, the client receives and routes the url. It also
		// allows client-side URLs to be bookmarked.

		// Single directory level - no need to exclude "api"
		registry.addViewController("/{x:[\\w\\-]+}")
				.setViewName("forward:/index.html");
		// Multi-level directory path, need to exclude "api" on the first part of the path
		registry.addViewController("/{x:^(?!api$).*$}/**/{y:[\\w\\-]+}")
				.setViewName("forward:/index.html");
		registry.addViewController("/").setViewName("forward:/index.html");
	}
}
