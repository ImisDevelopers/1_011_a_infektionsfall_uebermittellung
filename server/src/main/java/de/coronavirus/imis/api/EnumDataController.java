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
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;

@RestController
@RequestMapping("/api/enum-data")
public class EnumDataController {
  private static final Pattern RE_TEXTFILE_ENTRY = Pattern.compile("^\\s*(?!#)(?<ENTRY>.*?)\\s*$");
  private List<String> healthInsuranceCompanies;

  @Autowired
  public EnumDataController() {
    try (BufferedReader hicReader = new BufferedReader(new InputStreamReader(
      this.getClass().getClassLoader().getResourceAsStream("health_insurance_companies.txt"), "UTF-8"))) {

      this.healthInsuranceCompanies = hicReader.lines()
        .filter((String line) -> RE_TEXTFILE_ENTRY.matcher(line).matches())
        .map(String::trim)
        .sorted()
        .collect(Collectors.toList());

    } catch (IOException ioe) {
      ioe.printStackTrace();
      this.healthInsuranceCompanies = new ArrayList<>();
    }
  }

  @GetMapping("/health-insurance-companies")
  public List<String> getHealthInsuranceCompanies(
    @RequestParam(required = false) String search,
    @RequestParam(required = false) Optional<Integer> count) {

    Stream<String> result;
    if (this.healthInsuranceCompanies != null) {
      result = this.healthInsuranceCompanies.parallelStream();

      if (search != null) {
        result = result.filter((var companyName) -> companyName.contains(search));
      }

      if (count.isPresent()) {
        result = result.limit(count.get());
      }
    } else {
      result = Arrays.stream(new String[0]);
    }

    return result.collect(Collectors.toList());
  }
}
