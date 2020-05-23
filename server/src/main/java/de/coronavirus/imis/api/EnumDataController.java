package de.coronavirus.imis.api;

import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
@RequiredArgsConstructor
public class EnumDataController {

  private final TaskScheduler taskScheduler;
  private final ObjectMapper jsonMapper;
  private final ClientHttpRequestFactory requestFactory;

  private static final long HI_COMPANIES_CACHE_LIFETIME_MILLIS = 60 * 60 * 1000;
  private List<String> healthInsuranceCompanies;

  @Autowired
  public EnumDataController(TaskScheduler taskScheduler, ObjectMapper jsonMapper) {
    this.taskScheduler = taskScheduler;
    this.jsonMapper = jsonMapper;

    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    requestFactory.setConnectTimeout(7000);
    requestFactory.setReadTimeout(5000);
    this.requestFactory = requestFactory;

    FetchHealthInsuranceCompanies fhicTask = new FetchHealthInsuranceCompanies();
    this.taskScheduler.schedule(fhicTask, fhicTask.new TaskTrigger());
  }

  @GetMapping("/health-insurance-companies")
  public Stream<String> getHealthInsuranceCompanies(
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

    return result;
  }

  public class FetchHealthInsuranceCompanies implements Runnable {
    private boolean lastRunSuccessful;

    public void run() {
      try {
        // Fetch recent data
        ClientHttpResponse companiesResponse = EnumDataController.this.requestFactory.createRequest(
          new URI("https://www.gesetzlichekrankenkassen.de/ajax/kassenliste.php"),
          HttpMethod.GET
        ).execute();

        // Parse as JSON
        Charset encoding = companiesResponse.getHeaders().getContentType().getCharset();
        ExternalHICompanyEntry[] companyEntries =
          jsonMapper.readValue(new InputStreamReader(companiesResponse.getBody(), encoding), ExternalHICompanyEntry[].class);

        companiesResponse.close();

        // Convert
        healthInsuranceCompanies = Arrays.stream(companyEntries)
          .map((var entry) -> entry.getLabel())
          .collect(Collectors.toList());

        this.lastRunSuccessful = true;
        return;

      } catch (URISyntaxException use) {
        // Should never happen, since hardcoded URI used
      } catch (IOException ioe) {
        ioe.printStackTrace();
      } finally {
        this.lastRunSuccessful = false;
      }
    }

    public class TaskTrigger implements Trigger {
      public Date nextExecutionTime(TriggerContext triggerContext) {
        if (triggerContext.lastCompletionTime() == null) {
          return new Date();

        } else {
          double factor = lastRunSuccessful ? 1.0 : 0.2;
          return new Date(Math.max(60 * 1000,
            triggerContext.lastCompletionTime().getTime() +
            HI_COMPANIES_CACHE_LIFETIME_MILLIS));
        }
      }
    }
  }

  @Data
  public static class ExternalHICompanyEntry {
    private String kid;
    private String label;
  }
}
