package de.coronavirus.imis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabTest {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @ApiModelProperty(required = true)
    private String testId;

    @ManyToOne
    @JsonIgnore
    private Laboratory laboratory;

    private TestStatus testStatus;

    private TestType testType;

    private String comment;

    private byte[] report;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class LabTestBuilder {

    }

}
