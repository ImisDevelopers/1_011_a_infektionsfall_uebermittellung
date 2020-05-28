package de.coronavirus.imis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.OffsetDateTime;

import de.coronavirus.imis.api.exception.FailureWithDetailsException;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
	// Use constant names from LabTest.Constraints
	@UniqueConstraint(
		name = "LAB_UNIQUE_TEST_ID",
		columnNames = {"testId", "laboratory_id"}
	)
})
public class LabTest {
	public enum Constraint {
		LAB_UNIQUE_TEST_ID;
	}

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

	private TestMaterial testMaterial;

	private String comment;

	private byte[] report;

	private OffsetDateTime lastUpdate;

	@JsonPOJOBuilder(withPrefix = "")
	public static final class LabTestBuilder {

	}


	public static class ConstraintViolationException extends FailureWithDetailsException {
		public ConstraintViolationException(LabTest.Constraint constraint) {
			super("ConstraintViolation", "Violation of a model constraint", constraint);
		}
		public ConstraintViolationException(String constraintName) {
			this(Constraint.valueOf(constraintName));
		}

		protected Response createCustomResponse(Object constraint) {
			return new Response((Constraint) constraint);
		}

		@Data
		@ApiModel("LabTestConstraintViolation")
		public static class Response extends FailureWithDetailsException.Response {
			private Constraint constraint;

			public Response(Constraint constraint) {
				this.constraint = constraint;
			}
		}
	}

}
