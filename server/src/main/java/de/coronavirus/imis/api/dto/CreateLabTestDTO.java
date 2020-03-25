package de.coronavirus.imis.api.dto;

import lombok.Data;

@Data
public class CreateLabTestDTO {
    private String patientId;
    private String laboratoryId;
    private String testId;
    private String comment;
}
