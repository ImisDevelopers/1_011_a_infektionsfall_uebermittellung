package de.coronavirus.imis.services;

import de.coronavirus.imis.api.dto.CreateLabTestDTO;
import de.coronavirus.imis.api.dto.UpdateTestStatusDTO;
import de.coronavirus.imis.domain.*;
import de.coronavirus.imis.repositories.IncidentRepository;
import de.coronavirus.imis.repositories.LaboratoryRepository;
import de.coronavirus.imis.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
TODO
This class is called deprectaed because it enables the frontend to work with  LabTestDTOs and QuarantineDTOs
instead of natively using IncidentDTOs.
Next steps: Build an Incident API and migrate the Frontend.
 */
@Service
public class DeprecatedIncidentService {

	@Autowired
	IncidentRepository incidentRepository;
	@Autowired
	LaboratoryRepository laboratoryRepository;
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	RandomService randomService;


	public Incident addIncident (CreateLabTestDTO info)
	{
		var incident = new TestIncident()
				.setTestId(info.getTestId())
				.setComment(info.getComment())
				.setType(info.getTestType())
				.setStatus(TestStatus.TEST_SUBMITTED)
				.setLaboratory(laboratoryRepository.findById(info.getLaboratoryId()).orElseThrow(LaboratoryNotFoundException::new))
				.setEventType(EventType.TEST_SUBMITTED_IN_PROGRESS)
				.setPatient(patientRepository.findById(info.getPatientId()).orElseThrow(PatientNotFoundException::new))
				.setId(randomService.getRandomString(10));

		incidentRepository.saveAndFlush(incident);
		return incident;
	}

	public Incident updateIncident (UpdateTestStatusDTO update)
	{
		// incident by the fact that its a test and the ID.
		return null;

	}

	// Quarantine Incidents
/*
	public Incident addIncident (CreateQuarantinePeriodDTO info)
	{
		return null;
	}

	public Incident addIncident (UpdateQuarantinePeriodDTO info)
	{
		return null;
	}
*/
}
