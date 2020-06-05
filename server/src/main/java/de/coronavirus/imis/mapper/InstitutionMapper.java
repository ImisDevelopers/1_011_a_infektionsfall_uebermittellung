package de.coronavirus.imis.mapper;

import de.coronavirus.imis.api.dto.CreateInstitutionDTO;
import de.coronavirus.imis.api.dto.InstitutionDTO;
import de.coronavirus.imis.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class InstitutionMapper {

	public InstitutionImpl toInstitution(CreateInstitutionDTO dto) {
		switch (dto.getInstitutionType()) {
			case CLINIC:
				return this.toClinic(dto);
			case DEPARTMENT_OF_HEALTH:
				return this.toDepartmentOfHealth(dto);
			case DOCTORS_OFFICE:
				return this.toDoctor(dto);
			case LABORATORY:
				return this.toLaboratory(dto);
			case TEST_SITE:
				return this.toTestSite(dto);
			default:
				throw new RuntimeException(String.format(
						"Creating institution of type '%s' currently not implemented",
						dto.getInstitutionType()));
		}
	}

	@Mapping(target = "users", ignore = true)
	public abstract Clinic toClinic(CreateInstitutionDTO dto);

	@Mapping(target = "users", ignore = true)
	public abstract DepartmentOfHealth toDepartmentOfHealth(CreateInstitutionDTO dto);

	@Mapping(target = "users", ignore = true)
	public abstract Doctor toDoctor(CreateInstitutionDTO dto);

	@Mapping(target = "users", ignore = true)
	public abstract Laboratory toLaboratory(CreateInstitutionDTO dto);

	@Mapping(target = "users", ignore = true)
	public abstract TestSite toTestSite(CreateInstitutionDTO dto);

	@Mapping(target = "institutionType", source = "type")
	public abstract InstitutionDTO toInstitutionDTO(InstitutionImpl institution);
}
