package de.coronavirus.imis.api.dto;

import lombok.Data;

@Data
public class RequestLabTestDTO {
	private String patientId;
	private String laboratoryId;
	private String doctorId;
}
