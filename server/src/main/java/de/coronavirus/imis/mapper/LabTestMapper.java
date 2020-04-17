package de.coronavirus.imis.mapper;

import de.coronavirus.imis.api.dto.CreateLabTestDTO;
import de.coronavirus.imis.domain.*;
import de.coronavirus.imis.repositories.LaboratoryRepository;
import de.coronavirus.imis.repositories.PatientCaseRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper
public abstract class LabTestMapper {

	@Autowired
	protected LaboratoryRepository laboratoryRepository;
	@Autowired
	protected PatientCaseRepository patientCaseRepository;

	// TODO: Den Java Code als String zu schreiben finde ich nicht so schön, da nicht typesafe.
	// 		 Kann man das auch anders lösen?
	@Mapping(target = "laboratory", expression = "java( labById(dto.getLaboratoryId()) )")
	@Mapping(target = "patientCase", expression = "java( patientCaseById(dto.getPatientCaseId()) )")
	@Mapping(target = "testStatus", constant = "TEST_SUBMITTED_IN_PROGRESS")
	@Mapping(target = "id", ignore = true)
	public abstract LabTest toLabTest(CreateLabTestDTO dto);

	protected Laboratory labById(String id) {
		return laboratoryRepository
				.findById(id)
				.orElseThrow(LaboratoryNotFoundException::new);
	}

	protected PatientCase patientCaseById(String id) {
		return patientCaseRepository
				.findById(id)
				.orElseThrow(PatientNotFoundException::new);
	}
}
