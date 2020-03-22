package de.coronavirus.imis.api.dto;

import de.coronavirus.imis.domain.InstitutionType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
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
