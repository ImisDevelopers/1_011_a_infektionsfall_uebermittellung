package de.coronavirus.imis.api.dto;

import lombok.Data;

@Data
public class CreateLabTestDTO {
    public String patientId;
    public String laboratoryId;
    public String labInternalId;
}
