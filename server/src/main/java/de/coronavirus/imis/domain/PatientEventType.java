package de.coronavirus.imis.domain;

/**
 *
 */
public enum PatientEventType {
	REGISTERED,
	SUSPECTED,
	CONFIRMED,
	PATIENT_RECOVERED,
	PATIENT_DEAD,
	DOCTORS_VISIT,
	// DOCTORS_VISIT, // TODO: Do we need Doctors Visit?
	HOSPITALIZED,
	INTENSIVE_CARE,
	QUARANTINED
}
