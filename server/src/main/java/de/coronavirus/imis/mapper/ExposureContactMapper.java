package de.coronavirus.imis.mapper;

import de.coronavirus.imis.api.dto.ExposureContactDTO;
import de.coronavirus.imis.domain.ExposureContact;
import de.coronavirus.imis.mapper.PatientMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = PatientMapper.class)
public abstract class ExposureContactMapper {

  @Mapping(target = "id", source = "common.id")
  @Mapping(target = "context", source = "common.context")
  @Mapping(target = "dateOfContact", source = "common.dateOfContact")
  @Mapping(target = "comment", source = "common.comment")
  public abstract ExposureContact toExposureContact(ExposureContactDTO.ToServer dto);

  @Mapping(target = "common", source = ".")
  public abstract ExposureContactDTO.FromServer toExposureContactDTO(ExposureContact contact);
}
