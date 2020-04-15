package de.coronavirus.imis.api.dto;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import de.coronavirus.imis.domain.TestStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTestStatusDTO {
    private TestStatus status;
    private String comment;
    private String testId;
    private byte[] file;
}
