package de.coronavirus.imis.services.incidents;

import de.coronavirus.imis.api.dto.CreateLabTestDTO;
import de.coronavirus.imis.api.dto.RequestQuarantineDTO;
import de.coronavirus.imis.api.dto.UpdateTestStatusDTO;
import de.coronavirus.imis.domain.*;
import de.coronavirus.imis.mapper.PatientMapper;
import de.coronavirus.imis.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Deprecated
public class WriteIncidentService {

	private final TestIncidentRepository testIncidentRepo;
	private final LaboratoryRepository laboratoryRepo;
	private final PatientRepository patientRepo;
	private final PatientMapper patientMapper;
	private final QuarantineIncidentRepository quarantineIncidentRepo;
	private final AdministrativeIncidentRepository adminIncidentRepo;
	private final HospitalizationIncidentRepository hospIncidentRepo;
	private final DoctorRepository doctorRepo;

	@Transactional
	public TestIncident addTestIncident(CreateLabTestDTO info) {

		if(info.getEventDate()==null)
			info.setEventDate(LocalDate.now());

		var incident = (TestIncident) new TestIncident()
				.setTestId(info.getTestId())
				.setTestMaterial(info.getTestMaterial())
				.setComment(info.getComment())
				.setType(info.getTestType())
				.setStatus(TestStatus.TEST_SUBMITTED)
				.setLaboratory(laboratoryRepo.findById(info.getLaboratoryId()).orElseThrow(LaboratoryNotFoundException::new))
				.setEventType(EventType.TEST_SUBMITTED_IN_PROGRESS)
				.setPatient(patientRepo.findById(info.getPatientId()).orElseThrow(PatientNotFoundException::new))
				.setEventDate(info.getEventDate());

		testIncidentRepo.saveAndFlush(incident);
		return incident;
	}

	@Transactional
	public TestIncident updateTestIncident(String laboratoryId, UpdateTestStatusDTO update) {

		if(update.getEventDate()==null)
			update.setEventDate(LocalDate.now());

		var incident = testIncidentRepo.findByTestId(update.getTestId()).get(0);
		var laboratory = laboratoryRepo.findById(laboratoryId).orElseThrow();
		incident
				.setLaboratory(laboratory)
				.setStatus(update.getStatus())
				.setComment(update.getComment())
				.setReport(update.getFile())
				.setEventType(testStatusToEvent(update.getStatus()))
				.setEventDate(update.getEventDate());
		// ToDo bei setStatus und setEventType: Semantik? Sinnvoll so?

		testIncidentRepo.saveAndFlush(incident);
		return incident;
	}

	// Quarantine Incidents
	@Transactional
	public QuarantineIncident addOrUpdateQuarantineIncident(String patientId, RequestQuarantineDTO info) {
		// Patient & Date
		var patient = patientRepo.findById(patientId).orElseThrow();
		var until = patientMapper.parseDate(info.getDateUntil());

		// There's only one QuarantineIncident per Person which is why we can find it without Incident Id here.
		var incidentOptional = quarantineIncidentRepo.findByPatientId(patientId);
		var incident = incidentOptional.isEmpty()
				? new QuarantineIncident()
				: incidentOptional.get(0);

		if(info.getEventDate()==null)
			info.setEventDate(LocalDate.now());

		// Apply to Incident
		incident
				.setComment(info.getComment())
				.setUntil(until)
				.setEventType(info.getStatus() != null ? info.getStatus() : EventType.QUARANTINE_SELECTED)
				.setEventDate(info.getEventDate())
				.setPatient(patient);
		quarantineIncidentRepo.saveAndFlush(incident);

		return incident;
	}

	// Administrative Incidents

	// Todo: Pass in DTOs
	@Transactional
	public AdministrativeIncident addOrUpdateAdministrativeIncident(Patient patient,
																	Optional<Illness> illness,
																	EventType eventType,
																	LocalDate dateOfReporting,
																	LocalDate dateofIllness) {

		// There's only one AdministrativeIncident per Person
		// Once case support is enabled, it will be one Administrative Incident per Case

		var concreteIllness = illness.orElse(Illness.CORONA);

		dateOfReporting = dateOfReporting == null ? LocalDate.now() : dateOfReporting;
		dateofIllness = dateofIllness == null ? patient.getDateOfIllness() : dateofIllness;

		var incidentOptional = adminIncidentRepo.findByPatientId(patient.getId());
		AdministrativeIncident incident = incidentOptional.isEmpty() ? new AdministrativeIncident() : incidentOptional.get(0);

		incident
				.setIllness(concreteIllness)
				.setSymptoms(patient.getSymptoms())
				.setDateOfIllness(dateofIllness)
				.setDateOfReporting(dateOfReporting)
				.setEventType(eventType)
				.setEventDate(LocalDate.now())
				.setPatient(patient);

		adminIncidentRepo.saveAndFlush(incident);
		return incident;
	}

	// Update by Falldaten Form
	@Transactional
	public void deductIncidentUpdates(Patient newValues)
	{
		// Deduct Admin Incident Changes
		var adminOpt = adminIncidentRepo.findByPatientId(newValues.getId());
		if (!adminOpt.isEmpty())
		{
			AdministrativeIncident adminInc = adminOpt.get(0);
			if ( ! Objects.equals(adminInc.getDateOfIllness(), newValues.getDateOfIllness())
					|| !new HashSet<>(adminInc.getSymptoms()).equals(new HashSet<>(newValues.getSymptoms()))) {
				adminInc
						.setSymptoms(newValues.getSymptoms())
						.setDateOfIllness(newValues.getDateOfIllness())
						.setEventType(EventType.CASE_DATA_UPDATED);
				adminIncidentRepo.saveAndFlush(adminInc);
			}
		}

		// Deduct Hospitalization Changes
		/*
			Analog to Quarantine: Currently there is only one Hospitalization Incident per Person,
			which is technically incorrect.
			We need an agreed (frontend) strategy for handling hospitalization.
		 */
		boolean isIntensiveCare = Boolean.TRUE.equals(newValues.getOnIntensiveCareUnit());
		var hospitalizationOptional = hospIncidentRepo.findByPatientId(newValues.getId());
		HospitalizationIncident hospitalizationIncident;
		if (!hospitalizationOptional.isEmpty())
		{
			hospitalizationIncident = hospitalizationOptional.get(0);
			if (newValues.getDateOfHospitalization() == null
					&& isIntensiveCare == hospitalizationIncident.isIntensiveCare())
			{
				if (hospitalizationIncident.getEventDate() != null)
				{
					hospitalizationIncident.setReleasedOn(LocalDate.now());
					hospitalizationIncident.setEventType(EventType.HOSPITALIZATION_RELEASED);
				}
			}
			else
			{
				if (hospitalizationIncident.getReleasedOn() != null)
				{
					hospitalizationIncident.setReleasedOn(null); // Re-Use the incident.
					hospitalizationIncident.setEventType(EventType.HOSPITALIZATION_MANDATED);
				}
				hospitalizationIncident.setEventDate(newValues.getDateOfHospitalization());
				hospitalizationIncident.setIntensiveCare(isIntensiveCare);
			}
			hospIncidentRepo.saveAndFlush(hospitalizationIncident);
		}
		else if(newValues.getDateOfHospitalization() != null)
		{
			addHospitalizationIncident(newValues, newValues.getDateOfHospitalization(), isIntensiveCare);
		}
	}

	//SCHEDULED_FOR_TESTING
	@Transactional
	public AdministrativeIncident addOrUpdateAdministrativeIncident(Patient patient, String labId, String doctorId) {

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

		var incidentOptional = adminIncidentRepo.findByPatientId(patient.getId());
		AdministrativeIncident incident = incidentOptional.isEmpty() ? new AdministrativeIncident() : incidentOptional.get(0);

		incident
			.setResponsibleDoctor(doctor)
			.setEventType(EventType.SCHEDULED_FOR_TESTING)
			.setEventDate(LocalDate.now())
			.setPatient(patient);

		adminIncidentRepo.saveAndFlush(incident);

		return incident;
	}


	@Transactional
	public void addHospitalizationIncident(Patient patient, @NotNull LocalDate hospitalizedOn, Boolean intensiveCare) {

		boolean isIntensiveCare = Boolean.TRUE.equals(intensiveCare);

		var incident = (HospitalizationIncident) new HospitalizationIncident()
				.setIntensiveCare(isIntensiveCare)
				.setEventDate(hospitalizedOn)
				.setEventType(EventType.HOSPITALIZATION_MANDATED)
				.setPatient(patient);

		hospIncidentRepo.saveAndFlush(incident);

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
