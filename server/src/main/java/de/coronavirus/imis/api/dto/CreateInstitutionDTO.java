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
        dto.setInstitutionType(registerUserRequest.getInstitutionType());
        dto.setName(registerUserRequest.getName());
        dto.setStreet(registerUserRequest.getStreet());
        dto.setHouseNumber(registerUserRequest.getHouseNumber());
        dto.setZip(registerUserRequest.getZip());
        dto.setCity(registerUserRequest.getCity());
        dto.setEmail(registerUserRequest.getEmail());
        dto.setPhoneNumber(registerUserRequest.getPhoneNumber());
        dto.setComment(registerUserRequest.getComment());
        return dto;
    }
}
