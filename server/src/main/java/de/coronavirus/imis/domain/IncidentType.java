package de.coronavirus.imis.domain;

public enum IncidentType {

	// lowercase to support direct casting from api route.

	test(TestIncident.class),
	quarantine(QuarantineIncident.class),
	administrative(AdministrativeIncident.class);

	public final Class IMPLEMENTATION;

	IncidentType(Class implementation) {
		this.IMPLEMENTATION = implementation;
	}

}
