package de.coronavirus.imis.api.dto;

import lombok.Data;

@Data
public class RequestTestDTO {
    private String patientId;
    private String laboratoryId;
    private String doctorId;
}
