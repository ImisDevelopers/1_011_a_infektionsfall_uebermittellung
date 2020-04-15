package de.coronavirus.imis.api.dto;

import de.coronavirus.imis.domain.InstitutionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionDTO {
	private String id;
	private InstitutionType institutionType;
	private String name;
	private String street;
	private String houseNumber;
	private String zip;
	private String city;
	private String email;
	private String phoneNumber;
	private String comment;
}
