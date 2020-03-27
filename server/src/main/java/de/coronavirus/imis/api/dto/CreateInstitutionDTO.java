package de.coronavirus.imis.api.dto;

import lombok.Data;

import de.coronavirus.imis.domain.InstitutionType;

@Data
public class CreateInstitutionDTO {
    private InstitutionType institutionType;
    private String name;
    private String street;
    private String houseNumber;
    private String zip;
    private String city;
    private String email;
    private String phoneNumber;
    private String comment;
    private String id;

    public static CreateInstitutionDTO fromRegisterRequest(RegisterUserRequest registerUserRequest) {
        CreateInstitutionDTO dto = new CreateInstitutionDTO();
        dto.setInstitutionType(registerUserRequest.institutionType());
        dto.setName(registerUserRequest.name());
        dto.setStreet(registerUserRequest.street());
        dto.setHouseNumber(registerUserRequest.houseNumber());
        dto.setZip(registerUserRequest.zip());
        dto.setCity(registerUserRequest.city());
        dto.setEmail(registerUserRequest.email());
        dto.setPhoneNumber(registerUserRequest.phoneNumber());
        dto.setComment(registerUserRequest.comment());
        return dto;
    }
}
