package de.coronavirus.imis.api;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

import de.coronavirus.imis.repositories.PatientRepository;

@RestController
@RequestMapping("/api/enum-data")
public class EnumDataController {
  private final PatientRepository patientRepo;

  private static final Pattern RE_TEXTFILE_ENTRY = Pattern.compile("^\\s*(?!#)(?<ENTRY>.*?)\\s*$");
  private SortedSet<String> predefinedHealthInsuranceCompanies;

  @Autowired
  public EnumDataController(PatientRepository patientRepo) {
    this.patientRepo = patientRepo;

    try (BufferedReader hicReader = new BufferedReader(new InputStreamReader(
      this.getClass().getClassLoader().getResourceAsStream("health_insurance_companies.txt"), "UTF-8"))) {

      this.predefinedHealthInsuranceCompanies = hicReader.lines()
        .filter((String line) -> RE_TEXTFILE_ENTRY.matcher(line).matches())
        .map(String::trim)
        .sorted()
        .collect(Collectors.toCollection(() -> new TreeSet<>()));

    } catch (IOException ioe) {
      ioe.printStackTrace();
      this.predefinedHealthInsuranceCompanies = new TreeSet<>();
    }
  }

  @GetMapping("/health-insurance-companies")
  public HealthInsuranceCompanies getHealthInsuranceCompanies(
    @RequestParam(required = false) String search,
    @RequestParam(required = false) Optional<Integer> count) {

    SortedSet<String> userDefined = this.patientRepo.findDistinctInsuranceCompanies();
    userDefined.removeAll(this.predefinedHealthInsuranceCompanies);

    List<List<String>> sets = Arrays.stream(new SortedSet[] {
      this.predefinedHealthInsuranceCompanies,
      userDefined
    }).map((SortedSet healthInsuranceCompanies) -> {
      Stream<String> result = ((SortedSet<String>) healthInsuranceCompanies).parallelStream();

      if (search != null) {
        result = result.filter((var companyName) -> companyName.contains(search));
      }

      result = result.sorted();

      if (count.isPresent()) {
        result = result.limit(count.get());
      }

      return (List<String>) result.collect(Collectors.toList());
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
