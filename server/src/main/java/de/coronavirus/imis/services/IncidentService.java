package de.coronavirus.imis.services;

import de.coronavirus.imis.api.dto.CreateLabTestDTO;
import de.coronavirus.imis.api.dto.SendToQuarantineDTO;
import de.coronavirus.imis.api.dto.UpdateTestStatusDTO;
import de.coronavirus.imis.domain.*;
import de.coronavirus.imis.mapper.PatientMapper;
import de.coronavirus.imis.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

/*
TODO
This class is called deprectaed because it enables the frontend to work with  LabTestDTOs and QuarantineDTOs
instead of natively using IncidentDTOs.
Next steps: Build an Incident API and migrate the Frontend.
 */
@RequiredArgsConstructor
@Service
public class IncidentService {

	private final TestIncidentRepository testIncidentRepo;
	private final LaboratoryRepository laboratoryRepo;
	private final PatientRepository patientRepo;
	private final RandomService randomService;
	private final PatientMapper patientMapper;
	private final QuarantineIncidentRepository quarantineIncidentRepo;
	private final AdministrativeIncidentRepository adminIncidentRepo;
	private final DoctorRepository doctorRepo;

	@Transactional
	public TestIncident addIncident (CreateLabTestDTO info)
	{
		var incident = (TestIncident) new TestIncident()
				.setTestId(info.getTestId())
				.setComment(info.getComment())
				.setType(info.getTestType())
				.setStatus(TestStatus.TEST_SUBMITTED)
				.setLaboratory(laboratoryRepo.findById(info.getLaboratoryId()).orElseThrow(LaboratoryNotFoundException::new))
				.setEventType(EventType.TEST_SUBMITTED_IN_PROGRESS)
				.setPatient(patientRepo.findById(info.getPatientId()).orElseThrow(PatientNotFoundException::new))
				.setId(randomService.getRandomString(10));

		testIncidentRepo.saveAndFlush(incident);
		return incident;
	}

	@Transactional
	public TestIncident updateIncident (String labrotoryId, UpdateTestStatusDTO update)
	{
		var incident = testIncidentRepo.findByTestId(update.getTestId()).get(0);
		var laboratory = laboratoryRepo.findById(labrotoryId).orElseThrow();
		incident
				.setLaboratory(laboratory)
				.setStatus(update.getStatus())
				.setComment(update.getComment())
				.setReport(update.getFile())
				.setEventType(testStatusToEvent(update.getStatus()));
		// ToDo bei setStatus und setEventType: Semantik? Sinnvoll so?

		testIncidentRepo.saveAndFlush(incident);
		return incident;
	}

	// Quarantine Incidents
	@Transactional
	public QuarantineIncident addOrUpdateIncident (String patientId, SendToQuarantineDTO info)
	{
		// Patient & Date
		var patient = patientRepo.findById(patientId).orElseThrow();
		var until = patientMapper.parseDate(info.getDateUntil());

		// There's only one QuarantineIncident per Person which is why we can find it without Incident Id here.
		var incidentOptional = quarantineIncidentRepo.findByPatientId(patientId);
		var incident = incidentOptional.isEmpty()
				? (QuarantineIncident) new QuarantineIncident().setId(randomService.getRandomString(10)) // Set Id should happen in background
				: incidentOptional.get(0);

		// Apply to Incident
		incident
				.setComment(info.getComment())
				.setUntil(until)
				.setEventType(EventType.QUARANTINE_MANDATED)
				.setPatient(patient);
		quarantineIncidentRepo.saveAndFlush(incident);

		return incident;
	}

	// Administrative Incidents

	// Presumtion Event (Former Initial Event)
	@Transactional
	public AdministrativeIncident addIncident (Patient patient,
							 Optional<Illness> illness,
							 EventType eventType,
							 LocalDate dateOfReporting) {
		var concreteIllness = illness.orElse(Illness.CORONA);

		dateOfReporting = dateOfReporting==null ? LocalDate.now() : dateOfReporting;

		var incident = (AdministrativeIncident) new AdministrativeIncident()
				.setDateOfReporting(dateOfReporting)
				.setIllness(concreteIllness)
				.setEventType(eventType)
				.setPatient(patient)
				.setId(randomService.getRandomString(10));

		adminIncidentRepo.saveAndFlush(incident);
		return incident;
	}

	//SCHEDULED_FOR_TESTING
	@Transactional
	public AdministrativeIncident addIncident (Patient patient, String labId, String doctorId) {

		// Todo: Is this necessary? Why? Move it?
		final Laboratory laboratory = laboratoryRepo.findById(labId).orElseGet(() -> {
			Laboratory lab = new Laboratory();
			lab.setId(labId);
			return laboratoryRepo.save(lab);
		});
		final Doctor doctor = doctorRepo.findById(doctorId).orElseGet(() ->
				{
					var newDoctor = new Doctor();
					newDoctor.setId(doctorId);
					return doctorRepo.save(newDoctor);
				}
		);

		var incident = (AdministrativeIncident) new AdministrativeIncident()
				.setDateOfReporting(LocalDate.now())
				.setIllness(Illness.CORONA)
				.setResponsibleDoctor(doctor)
				.setEventType(EventType.SCHEDULED_FOR_TESTING)
				.setPatient(patient)
				.setId(randomService.getRandomString(10));
		adminIncidentRepo.saveAndFlush(incident);

		return incident;
	}

	private EventType testStatusToEvent(TestStatus input) {
		EventType result;
		switch (input) {
			case TEST_NEGATIVE:
				result = EventType.TEST_FINISHED_NEGATIVE;
				break;
			case TEST_SUBMITTED:
			case TEST_IN_PROGRESS:
				result = EventType.TEST_SUBMITTED_IN_PROGRESS;
				break;
			case TEST_POSITIVE:
				result = EventType.TEST_FINISHED_POSITIVE;
				break;
			default:
				result = EventType.TEST_FINISHED_INVALID;

		}
		return result;
	}
}
