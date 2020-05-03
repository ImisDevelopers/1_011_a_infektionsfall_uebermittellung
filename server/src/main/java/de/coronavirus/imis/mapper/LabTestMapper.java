package de.coronavirus.imis.mapper;

import de.coronavirus.imis.api.dto.CreateLabTestDTO;
import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.domain.Laboratory;
import de.coronavirus.imis.domain.LaboratoryNotFoundException;
import de.coronavirus.imis.repositories.LaboratoryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.OffsetDateTime;


@Mapper(imports = OffsetDateTime.class)
public abstract class LabTestMapper {

	@Autowired
	protected LaboratoryRepository laboratoryRepository;

	@Mapping(target = "laboratory", expression = "java( labById(dto.getLaboratoryId()) )")
	@Mapping(target = "testStatus", constant = "TEST_SUBMITTED")
	@Mapping(target = "lastUpdate", expression = "java( OffsetDateTime.now() )")
	@Mapping(target = "id", ignore = true)
	public abstract LabTest toLabTest(CreateLabTestDTO dto);

	protected Laboratory labById(String id) {
		return laboratoryRepository
				.findById(id)
				.orElseThrow(LaboratoryNotFoundException::new);
	}
}
