package de.coronavirus.imis.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.coronavirus.imis.services.SearchService;

@Configuration
public class HibernateSearchConfig {

	@Bean
	public SearchService searchService(EntityManagerFactory emf) {
		var searchService = new SearchService(emf);
		searchService.triggerIndexing();
		return searchService;
	}

}
