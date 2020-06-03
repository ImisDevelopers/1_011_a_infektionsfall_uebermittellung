package de.coronavirus.imis.services.incidents;

import de.coronavirus.imis.domain.Incident;
import de.coronavirus.imis.domain.TestIncident;
import de.coronavirus.imis.repositories.TestIncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
}
