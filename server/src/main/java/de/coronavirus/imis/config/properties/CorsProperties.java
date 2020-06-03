package de.coronavirus.imis.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "server.http.cors")
@AllArgsConstructor
@Getter
public class CorsProperties {
	private List<String> allowedOrigins;
}
