package de.coronavirus.imis.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Entity
@Value
@JsonDeserialize(builder = LabTest.LabTestBuilder.class)
@Builder
public class LabTest {
    @Id
    @GeneratedValue
    private String id;

    private String laborTestID;

    @ManyToOne
    @JsonIgnore
    private Laboratory laboratory;

    private TestStatus testStatus;

    @OneToOne
    private TestReport report;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class LabTestBuilder {

    }

}
