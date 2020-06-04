package de.coronavirus.imis.services.incidents;

import de.coronavirus.imis.domain.TestIncident;
import de.coronavirus.imis.repositories.TestIncidentRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
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

  public TestIncident getCurrentByTestAndLabId(String testId, String labId) {
  	try {
		return testIncidentRepository.findByTestIdAndLaboratory_id(testId, labId).get(0);
	} catch (IndexOutOfBoundsException e) {
  		throw new TestNotFoundException();
	  }
  }

  @Transactional
  public TestIncident setIncident(TestIncident test)
  {
	  return testIncidentRepository.save(test);

  }

  public class TestNotFoundException extends RuntimeException{}
}
