package de.coronavirus.imis.services;

import de.coronavirus.imis.api.dto.CreateLabTestDTO;
import de.coronavirus.imis.api.dto.SendToQuarantineDTO;
import de.coronavirus.imis.api.dto.UpdateTestStatusDTO;
import de.coronavirus.imis.domain.*;
import de.coronavirus.imis.mapper.PatientMapper;
import de.coronavirus.imis.repositories.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
	private final PatientMapper patientMapper;
	private final QuarantineIncidentRepository quarantineIncidentRepo;
	private final AdministrativeIncidentRepository adminIncidentRepo;
	private final DoctorRepository doctorRepo;
	private final AuditReader auditReader;
	private final ApplicationContext ctx;

	// Reading

	@Transactional
	public Optional<QuarantineIncident> getQuarantineIncident(String patientId) {
		final List<QuarantineIncident> incidents = getLog(QuarantineIncident.class, patientId, true);
		if (incidents != null && !incidents.isEmpty()) {
			return Optional.of(incidents.get(0));
		} else {
			return Optional.empty();
		}
	}

	@Transactional
	public List<Incident> getLog(String id, boolean byPatient) {
		List<Incident> result = new ArrayList<Incident>();

		result.addAll(getLog(TestIncident.class, id, byPatient));
		result.addAll(getLog(QuarantineIncident.class, id, byPatient));
		result.addAll(getLog(AdministrativeIncident.class, id, byPatient));

		result.sort(
				(Incident i1, Incident i2) -> i1.getVersionTimestamp().compareTo(i2.getVersionTimestamp())
		);

		return result;
	}

	@Transactional
	public <T extends Incident> List<T> getLog(Class<T> T, String id, boolean byPatient) {
		var query = auditReader.createQuery().forRevisionsOfEntity(T, true, false);
		if (byPatient) {
			query.add(AuditEntity.relatedId("patient").eq(id));
		} else {
			query.add(AuditEntity.id().eq(id));
		}
		return query.getResultList();
	}

	@Transactional
	public Incident getCurrent(String id) {

		String prefix = id.split("_")[0];
		switch (IncidentType.valueOf(prefix)) {
			case test:
				return testIncidentRepo.findById(id).orElseThrow();
			case quarantine:
				return quarantineIncidentRepo.findById(id).orElseThrow();
			case administrative:
				return adminIncidentRepo.findById(id).orElseThrow();
		}

		return null;
	}

	@Transactional
	public List<Incident> getCurrentByPatient(String patientId) {

		List<Incident> result = new ArrayList<>();

		result.addAll(testIncidentRepo.findByPatientId(patientId));
		result.addAll(quarantineIncidentRepo.findByPatientId(patientId));
		result.addAll(adminIncidentRepo.findByPatientId(patientId));

		return result;
	}

	@Transactional
	public List<Incident> getCurrentByPatient(String patientId, IncidentType type) {

		switch (type) {
			case test:
				return (List<Incident>) (List<?>) testIncidentRepo.findByPatientId(patientId);
			case quarantine:
				return (List<Incident>) (List<?>) quarantineIncidentRepo.findByPatientId(patientId);
			case administrative:
				return (List<Incident>) (List<?>) adminIncidentRepo.findByPatientId(patientId);
		}

		return null;
	}

	// Writing

	@Transactional
	public TestIncident addIncident(CreateLabTestDTO info) {
		var incident = (TestIncident) new TestIncident()
				.setTestId(info.getTestId())
				.setTestMaterial(info.getTestMaterial())
				.setComment(info.getComment())
				.setType(info.getTestType())
				.setStatus(TestStatus.TEST_SUBMITTED)
				.setLaboratory(laboratoryRepo.findById(info.getLaboratoryId()).orElseThrow(LaboratoryNotFoundException::new))
				.setEventType(EventType.TEST_SUBMITTED_IN_PROGRESS)
				.setPatient(patientRepo.findById(info.getPatientId()).orElseThrow(PatientNotFoundException::new));

		testIncidentRepo.saveAndFlush(incident);
		return incident;
	}

	@Transactional
	public TestIncident updateIncident(String laboratoryId, UpdateTestStatusDTO update) {
		var incident = testIncidentRepo.findByTestId(update.getTestId()).get(0);
		var laboratory = laboratoryRepo.findById(laboratoryId).orElseThrow();
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
	public QuarantineIncident addOrUpdateIncident(String patientId, SendToQuarantineDTO info) {
		// Patient & Date
		var patient = patientRepo.findById(patientId).orElseThrow();
		var until = patientMapper.parseDate(info.getDateUntil());

		// There's only one QuarantineIncident per Person which is why we can find it without Incident Id here.
		var incidentOptional = quarantineIncidentRepo.findByPatientId(patientId);
		var incident = incidentOptional.isEmpty()
				? new QuarantineIncident()
				: incidentOptional.get(0);

		// Apply to Incident
		incident
				.setComment(info.getComment())
				.setUntil(until)
				.setEventType(info.getStatus() != null ? info.getStatus() : EventType.QUARANTINE_SELECTED)
				.setPatient(patient);
		quarantineIncidentRepo.saveAndFlush(incident);

		return incident;
	}

	// Administrative Incidents

	// Presumtion Event (Former Initial Event)
	@Transactional
	public AdministrativeIncident addIncident(Patient patient,
											  Optional<Illness> illness,
											  EventType eventType,
											  LocalDate dateOfReporting) {
		var concreteIllness = illness.orElse(Illness.CORONA);

		dateOfReporting = dateOfReporting == null ? LocalDate.now() : dateOfReporting;

		var incident = (AdministrativeIncident) new AdministrativeIncident()
				.setDateOfReporting(dateOfReporting)
				.setIllness(concreteIllness)
				.setEventType(eventType)
				.setPatient(patient);

		adminIncidentRepo.saveAndFlush(incident);
		return incident;
	}

	//SCHEDULED_FOR_TESTING
	@Transactional
	public AdministrativeIncident addIncident(Patient patient, String labId, String doctorId) {

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
				.setPatient(patient);
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
