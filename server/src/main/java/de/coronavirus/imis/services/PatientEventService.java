package de.coronavirus.imis.services;

import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientEvent;
import de.coronavirus.imis.domain.PatientEventType;
import de.coronavirus.imis.repositories.DoctorRepository;
import de.coronavirus.imis.repositories.LaboratoryRepository;
import de.coronavirus.imis.repositories.PatientEventRepository;
import de.coronavirus.imis.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PatientEventService {

	private final PatientEventRepository patientEventRepository;
	private final LaboratoryRepository laboratoryRepository;
	private final DoctorRepository doctorRepository;
	private final PatientRepository patientRepository;
	private final AuthService authService;

	public void createPatientEvent(Patient patient, PatientEventType eventType, OffsetDateTime eventTimestamp, String comment) {
		var patientEvent = new PatientEvent()
				.setPatientEventType(eventType)
				.setPatient(patient)
				.setEventTimestamp(eventTimestamp)
				.setCreationTimestamp(OffsetDateTime.now())
				.setUser(authService.getCurrentUser())
				.setComment(comment);
		patientEventRepository.save(patientEvent);
	}

	// TODO: Do we need DoctorsVisit?
//	@Transactional
//	public PatientEvent createScheduledEvent(Patient patient, String labId, String doctorId) {
//		final Laboratory laboratory = laboratoryRepository.findById(labId).orElseGet(() -> {
//			Laboratory lab = new Laboratory();
//			lab.setId(labId);
//			return laboratoryRepository.save(lab);
//		});
//		final Doctor doctor = doctorRepository.findById(doctorId).orElseGet(() ->
//				{
//					var newDoctor = new Doctor();
//					newDoctor.setId(doctorId);
//					return doctorRepository.save(newDoctor);
//				}
//		);
//
//		patient.setPatientStatus(PatientEventType.DOCTORS_VISIT);
//		patientRepository.save(patient);
//		var event = new PatientEvent()
//				.setEventTimestamp(Timestamp.from(Instant.now()))
//				.setPatientEventType(PatientEventType.DOCTORS_VISIT)
//				.setIllness(Illness.CORONA)
//				.setResponsibleDoctor(doctor)
//				.setPatient(patient);
//
//		return patientEventRepository.save(event);
//	}

	public List<PatientEvent> getAllForPatient(Patient patient) {
		return patientEventRepository.findAllByPatient(patient);
	}


	public PatientEvent findFirstByPatientOrderByEventTimestampDesc(Patient patient) {
		return patientEventRepository.findFirstByPatientOrderByEventTimestampDesc(patient);
	}
}
