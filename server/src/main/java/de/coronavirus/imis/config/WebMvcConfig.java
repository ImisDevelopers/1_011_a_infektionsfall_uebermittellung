package de.coronavirus.imis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("https://imis-prototyp.de", "https://staging.imis-prototyp.de",
						"http://imis-prototyp.de", "http://localhost:8080")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowCredentials(true);
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
