package de.coronavirus.imis.services.incidents;

import de.coronavirus.imis.domain.TestIncident;
import de.coronavirus.imis.repositories.TestIncidentRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

  public Optional<TestIncident> getCurrentByTestAndLabId(String testId, String labId) {
  	var result = testIncidentRepository.findByTestIdAndLaboratory_id(testId, labId);
  	if (!result.isEmpty())
  		return Optional.empty();
  	else
  		return Optional.of(result.get(0));
  }

  @Transactional
  public TestIncident setIncident(TestIncident test)
  {
	  return testIncidentRepository.save(test);

  }
}
