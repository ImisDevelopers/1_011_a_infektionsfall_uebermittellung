package de.coronavirus.imis.services.incidents;

import de.coronavirus.imis.domain.Incident;
import de.coronavirus.imis.domain.TestIncident;
import de.coronavirus.imis.repositories.TestIncidentRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Test;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestIncidentService {

  private final CommonIncidentService incidentService;
  private final TestIncidentRepository testIncidentRepository;

  public List<TestIncident> getIncidentLogByPatientId(String patientId) {
    return incidentService.getIncidentLogByPatientId(TestIncident.class, patientId);
  }

  public List<TestIncident> getCurrentByPatientId(String patientId) {
    return testIncidentRepository.findByPatientId(patientId);
  }

  public TestIncident getCurrentByTestId(String testId) {
  	return testIncidentRepository.findByTestId(testId).get(0);
  }

  @Transactional
  public TestIncident setIncident(TestIncident test)
  {
  	return testIncidentRepository.save(test);
  }

}
