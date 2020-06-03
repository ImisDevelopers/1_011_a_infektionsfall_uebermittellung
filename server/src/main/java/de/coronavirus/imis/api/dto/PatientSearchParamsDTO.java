package de.coronavirus.imis.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.coronavirus.imis.domain.EventType;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientSearchParamsDTO {

	private String firstName;
	private String lastName;
	private String id;
	private String gender;

	private String email;
	private String phoneNumber;
	private String street;
	private String houseNumber;
	private String zip;
	private String city;

	private String insuranceCompany;
	private String insuranceMembershipNumber;

	private String order;
	private String orderBy;

	private Long offsetPage;
	private Long pageSize;

	private String doctorId;
	private String laboratoryId;
	private boolean includePatientEvents;

	private EventType patientStatus;

	// TODO Setting this field has no effect on search results (see SearchService.beanProperties)
	private List<EventType> quarantineStatus;

	public Long getPageSize() {
		if (pageSize != null && pageSize > 0) {
			return pageSize;
		} else {
			return 20L;
		}
	}

	public Long getOffsetPage() {
		if (offsetPage != null && offsetPage >= 0) {
			return offsetPage;
		} else {
			return 0L;
		}
	}
}
