package de.coronavirus.imis.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class AggregationResultZip {
    private Integer Zip;
    private Long patientCount;
    private String eventType;
}
