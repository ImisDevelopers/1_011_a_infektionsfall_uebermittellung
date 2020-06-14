package de.coronavirus.imis.api;

import de.coronavirus.imis.api.dto.CountryDto;
import de.coronavirus.imis.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling available countries
 * For List of countries for different languages, see https://github.com/umpirsky/country-list
 */
@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
public class CountryController {

	private final CountryService countryService;

	@GetMapping
	public List<CountryDto> getCountries(@RequestParam(required = false) String language) {
		final String languageCode = language != null && language.length() > 0 ? language : "DE";
		return this.countryService.getCountries(languageCode);
	}

}
