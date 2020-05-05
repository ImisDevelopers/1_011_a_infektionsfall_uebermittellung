package de.coronavirus.imis.domain;

public enum IncidentType {

	TEST(TestIncident.class),
	QUARANTINE(QuarantineIncident.class),
	ADMINISTRATIVE(AdministrativeIncident.class);

	public final Class IMPLEMENTATION;

	IncidentType(Class implementation) {
		this.IMPLEMENTATION = implementation;
	}

}
