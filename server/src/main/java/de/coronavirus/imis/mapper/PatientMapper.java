package de.coronavirus.imis.mapper;

import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.domain.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(imports = OffsetDateTime.class)
public abstract class PatientMapper {

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

	@Mapping(target = "patientStatus", defaultValue = "REGISTERED")
	@Mapping(target = "creationTimestamp", expression = "java( OffsetDateTime.now() )")
	@Mapping(target = "dateOfBirth", dateFormat = DATE_FORMAT)
	@Mapping(target = "dateOfDeath", dateFormat = DATE_FORMAT)
	@Mapping(target = "dateOfHospitalization", dateFormat = DATE_FORMAT)
	@Mapping(target = "dateOfIllness", dateFormat = DATE_FORMAT)
	@Mapping(target = "quarantineUntil", dateFormat = DATE_FORMAT)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "events", ignore = true)
	public abstract Patient toPatient(CreatePatientDTO createPatient);

	public LocalDate parseDate(String localDateString) {
		LocalDate localDate = null;
		if (localDateString != null && !localDateString.isBlank()) {
			localDate = LocalDate.parse(localDateString, DATE_FORMATTER);
		}
		return localDate;
	}
}
