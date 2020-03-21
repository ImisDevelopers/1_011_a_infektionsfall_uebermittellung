package de.coronavirus.imis.api.dto;

import lombok.Data;

@Data
public class CreateLabTestDto {
    private String patientId;
    private String laboratoryId;
    private String labInternalId;
}
