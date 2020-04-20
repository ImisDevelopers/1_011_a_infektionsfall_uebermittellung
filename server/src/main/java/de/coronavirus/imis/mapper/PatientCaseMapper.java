package de.coronavirus.imis.mapper;

import de.coronavirus.imis.api.dto.PatientCaseDTO;
import de.coronavirus.imis.domain.PatientCase;
import org.mapstruct.Mapper;

@Mapper
public abstract class PatientCaseMapper {

	public abstract PatientCaseDTO toPatientCaseDTO(PatientCase patientCase);
}

