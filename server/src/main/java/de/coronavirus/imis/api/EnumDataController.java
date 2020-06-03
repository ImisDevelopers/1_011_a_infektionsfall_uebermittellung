package de.coronavirus.imis.api;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import de.coronavirus.imis.repositories.PatientRepository;

@RestController
@RequestMapping("/api/enum-data")
@Slf4j
public class EnumDataController {
	private final PatientRepository patientRepo;

	private static final Pattern RE_TEXTFILE_ENTRY = Pattern.compile("^\\s*(?!#)(?<ENTRY>.*?)\\s*$");
	private SortedSet<String> predefinedHealthInsuranceCompanies;

	@Autowired
	public EnumDataController(PatientRepository patientRepo) {
		this.patientRepo = patientRepo;

		String resourceFileName = "enum-data/health_insurance_companies.txt";
		InputStream resourceStream = this.getClass().getClassLoader()
				.getResourceAsStream(resourceFileName);

		if (resourceStream != null) {
			try (BufferedReader hicReader = new BufferedReader(new InputStreamReader(
					resourceStream, "UTF-8"))) {

				this.predefinedHealthInsuranceCompanies = hicReader.lines()
						.filter((String line) -> RE_TEXTFILE_ENTRY.matcher(line).matches())
						.map(String::trim)
						.sorted()
						.collect(Collectors.toCollection(() -> new TreeSet<>()));

				log.info("Insurance Company data loaded");

			} catch (IOException ioe) {
				log.warn("Insurance Company loading failed with error", ioe);
				this.predefinedHealthInsuranceCompanies = new TreeSet<>();
			}
		} else {
			log.warn("Insurance Company loading: Could not find resource file '" + resourceFileName + "'");
		}
	}

	@GetMapping("/health-insurance-companies")
	public HealthInsuranceCompanies getHealthInsuranceCompanies() {

		SortedSet<String> userDefined = this.patientRepo.findDistinctInsuranceCompanies();
		userDefined.removeAll(this.predefinedHealthInsuranceCompanies);

		List<List<String>> sets = Arrays.stream(new SortedSet[]{
				this.predefinedHealthInsuranceCompanies,
				userDefined
		}).map((SortedSet healthInsuranceCompanies) -> {
			return ((SortedSet<String>) healthInsuranceCompanies).parallelStream()
					.sorted()
					.collect(Collectors.toList());
		}).collect(Collectors.toList());

		return new HealthInsuranceCompanies(sets.get(0), sets.get(1));
	}

	@Data
	@AllArgsConstructor
	public static class HealthInsuranceCompanies {
		private List<String> preDefined;
		private List<String> userDefined;
	}
}
