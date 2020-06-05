package de.coronavirus.imis.api.incidents;

import de.coronavirus.imis.api.exception.ApiExceptionHandler;
import de.coronavirus.imis.domain.TestIncident;
import de.coronavirus.imis.services.incidents.TestIncidentService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
  @ApiResponses({
		  @ApiResponse(code = 400, message = "CONSTRAINT_VIOLATION", response = ApiExceptionHandler.ExceptionResponse.class)
  })
  public TestIncident setTest(@RequestBody TestIncident test) {
	return testIncidentService.setIncident(test);
  }

  // Preferred way to to this would be via setTest, but Incident is not known by Frontend when this Method gets called.
  @PostMapping("/test/test-id")
  @ApiResponses({
		  @ApiResponse(code = 400, message = "TEST_NOT_FOUND", response = ApiExceptionHandler.ExceptionResponse.class)
  })
  public TestIncident setTestByTestAndLabId(@RequestBody TestIncident test) {
  	var existingOpt = testIncidentService.getCurrentByTestAndLabId(test.getTestId(), test.getLaboratory().getId());
	if (existingOpt.isPresent())
	{
		var existing = existingOpt.get();
		existing.setStatus(test.getStatus());
		existing.setEventDate(test.getEventDate());
		existing.setComment(test.getComment());
		existing.setEventType(test.getEventType());
		return testIncidentService.setIncident(existing);
	}
  	else
		throw new TestNotFoundException();
  }

  public class TestNotFoundException extends RuntimeException{}
}
