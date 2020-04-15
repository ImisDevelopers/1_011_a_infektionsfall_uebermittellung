package de.coronavirus.imis.domain;

/**
 * Events belonging to a lab test
 * Basically represents the status a lab test can have
 */
public enum LabTestEventType {
	TEST_SCHEDULED,
	TEST_SUBMITTED_IN_PROGRESS,
	TEST_FINISHED_POSITIVE,
	TEST_FINISHED_NEGATIVE,
	TEST_FINISHED_INVALID,
}
