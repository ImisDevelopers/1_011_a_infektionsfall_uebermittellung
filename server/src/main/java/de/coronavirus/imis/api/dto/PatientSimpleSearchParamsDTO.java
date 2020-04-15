package de.coronavirus.imis.api.dto;

import lombok.Data;

@Data
public class PatientSimpleSearchParamsDTO {

	private String query;

	private String order;
	private String orderBy;

	private Long offsetPage;
	private Long pageSize;

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
