package de.coronavirus.imis.mapper;

import java.time.LocalDate;

import de.coronavirus.imis.api.dto.ExposureContactDTO;
import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.ExposureContact;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.mapper.PatientMapper;
import de.coronavirus.imis.repositories.PatientRepository;
import de.coronavirus.imis.services.incidents.QuarantineIncidentService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(imports = {
  EventType.class,
  LocalDate.class
})
public abstract class ExposureContactMapper {

  @Autowired
  protected PatientRepository patientRepo;

  @Autowired
  protected QuarantineIncidentService quarantineIncidentService;

  @Mapping(target = "id", source = "common.id")
  @Mapping(target = "source", expression = "java( patientRepo.findById(dto.getSource()).orElse(null) )")
  @Mapping(target = "contact", expression = "java( patientRepo.findById(dto.getContact()).orElse(null) )")
  @Mapping(target = "context", source = "common.context")
  @Mapping(target = "dateOfContact", source = "common.dateOfContact")
  @Mapping(target = "comment", source = "common.comment")
  public abstract ExposureContact toExposureContact(ExposureContactDTO.ToServer dto);

  @Mapping(target = "common", source = ".")
  public abstract ExposureContactDTO.FromServer toExposureContactDTO(ExposureContact contact);

  @Mapping(target = "infected", expression = "java( patient.getPatientStatus() == EventType.TEST_FINISHED_POSITIVE )")
  @Mapping(target = "inQuarantine", expression = "java( quarantineIncidentService.hasActiveQuarantine(patient) )")
  public abstract ExposureContactDTO.ContactView toContactView(Patient patient);
}
