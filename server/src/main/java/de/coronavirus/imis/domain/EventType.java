package de.coronavirus.imis.domain;

public enum EventType {
	REGISTERED,
	SUSPECTED,
	ORDER_TEST,
	SCHEDULED_FOR_TESTING,
	TEST_SUBMITTED_IN_PROGRESS,
	TEST_FINISHED_POSITIVE,
	TEST_FINISHED_NEGATIVE,
	TEST_FINISHED_INVALID,
	TEST_FINISHED_RECOVERED,
	TEST_FINISHED_NOT_RECOVERED,
	PATIENT_DEAD,
	DOCTORS_VISIT,
	QUARANTINE_SELECTED, /* Vorgemerkt */
	QUARANTINE_MANDATED,
	QUARANTINE_RELEASED,
	QUARANTINE_PROFESSIONBAN_RELEASED,
	HOSPITALIZATION_MANDATED,
	HOSPITALIZATION_RELEASED,
	CASE_DATA_UPDATED,
}
