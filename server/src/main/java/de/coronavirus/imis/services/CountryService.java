package de.coronavirus.imis.services;

import de.coronavirus.imis.api.dto.CountryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CountryService {

	private final Map<String, List<CountryDto>> countryCache = new HashMap<>(); // Language Code to List of Countries

	public CountryService() throws IOException {
		log.info("Loading countries");
		final var pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
		final Resource[] countryRessources = pathMatchingResourcePatternResolver.getResources("countries/countries*.csv");
		for (Resource countryRessource : countryRessources) {
			if (countryRessource.getFilename() == null || countryRessource.getFilename().length() < 10) {
				log.warn("Invalid Country File: " + countryRessource.toString());
				continue;
			}
			// File pattern: countries_<code>.csv
			final String code = countryRessource.getFilename().substring(10, countryRessource.getFilename().length() - 4);
			final List<CountryDto> countries = this.loadCountries(countryRessource);
			this.countryCache.put(code, countries);
			log.info("Loaded " + countries.size() + " countries for " + code);
		}
		log.info("Countries laoded");
	}

	/**
	 * Returns a list of countries with labels in the specified language
	 *
	 * @param languageCode language for the labels
	 * @return list of countries with code and name
	 */
	public List<CountryDto> getCountries(String languageCode) {
		return this.countryCache.get(languageCode);
	}

	private List<CountryDto> loadCountries(Resource resource) throws IOException {
		return this.loadRessource(resource).stream().map(line -> {
			final String[] lineSplit = line.split(",");
			return new CountryDto(lineSplit[0], lineSplit[1]);
		}).collect(Collectors.toList());
	}

	private List<String> loadRessource(Resource resource) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
			return reader.lines().collect(Collectors.toList());
		}
	}

}
