package de.coronavirus.imis.api.dto;

import de.coronavirus.imis.domain.InstitutionType;
import lombok.Data;

@Data
public class CreateInstitutionDTO {
    public String name;
    public String houseNumber;
    public String street;
    public String city;
    public String zip;
    public String email;
    public String phoneNumber;
    public String comment;
    public InstitutionType institutionType;
}
