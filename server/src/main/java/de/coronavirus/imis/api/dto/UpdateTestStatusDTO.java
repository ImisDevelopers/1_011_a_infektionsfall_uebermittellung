package de.coronavirus.imis.api.dto;

import lombok.Data;

@Data
public class UpdateTestStatusDTO {
	private String status;
	private String comment;
	private String testId;
	private byte[] file;
}
