package de.coronavirus.imis.services;

import de.coronavirus.imis.api.dto.CreatePatientCaseDTO;
import de.coronavirus.imis.domain.PatientCase;
import de.coronavirus.imis.domain.PatientNotFoundException;
import de.coronavirus.imis.repositories.PatientCaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PatientCaseService {

	private final PatientCaseRepository patientCaseRepository;
	private final PatientService patientService;

	public List<PatientCase> getCasesForPatient(String patientId) {
		return this.patientCaseRepository.findAllByPatient_Id(patientId);
	}

	public PatientCase createPatientCase(CreatePatientCaseDTO createPatientCaseDTO) {
		final var patient = this.patientService
				.findPatientById(createPatientCaseDTO.getPatientId())
				.orElseThrow(PatientNotFoundException::new);

		final var patientCase = new PatientCase()
				.setPatient(patient)
				.setLabTests(new ArrayList<>())
				.setSpeedOfSymptomsOutbreak(createPatientCaseDTO.getSpeedOfSymptomsOutbreak())
				.setSymptoms(createPatientCaseDTO.getSymptoms())
				.setIllness(createPatientCaseDTO.getIllness());

		return patientCaseRepository.save(patientCase);
	}

}
