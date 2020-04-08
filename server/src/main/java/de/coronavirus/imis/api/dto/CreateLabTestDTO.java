package de.coronavirus.imis.api.dto;

import de.coronavirus.imis.domain.TestType;
import lombok.Data;

@Data
public class CreateLabTestDTO {
    private String patientId;
    private String laboratoryId;
    private String testId;
    private String comment;
    private TestType testType;
}
