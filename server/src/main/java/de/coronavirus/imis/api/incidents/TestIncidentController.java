package de.coronavirus.imis.api.incidents;

import de.coronavirus.imis.domain.TestIncident;
import de.coronavirus.imis.services.incidents.TestIncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/incidents")
public class TestIncidentController {

  private final TestIncidentService testIncidentService;

  @PostMapping("/test/patient")
  public Map<String, List<TestIncident>> getPatientsCurrentByType(@RequestBody List<String> patientIds) {
    var result = new HashMap<String, List<TestIncident>>();
    for (String patientId : patientIds)
      result.put(patientId, testIncidentService.getCurrentByPatientId(patientId));
    return result;
  }

  @PostMapping("/test")
  public TestIncident writeTest(@RequestBody TestIncident test) {
	return testIncidentService.writeIncident(test);
  }

}
