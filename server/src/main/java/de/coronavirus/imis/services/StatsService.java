package de.coronavirus.imis.services;

import de.coronavirus.imis.api.dto.AggregationResultZip;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.PatientEvent;
import de.coronavirus.imis.domain.PatientInformation;
import de.coronavirus.imis.repositories.PatientEventRepository;
import de.coronavirus.imis.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsService {
	private final PatientRepository patientRepository;
	private final PatientEventRepository patientEventRepository;


	@Transactional(readOnly = true)
	public List<AggregationResultZip> resultZipList(String lowerBounds, String upperBounds) {
		final List<Patient> patients = patientRepository.
				findAllByZipBetween(lowerBounds, upperBounds);

		// TODO: Update: I do not think that Patient Event is what we want here
		// if we want to count active/resolved corona cases,
		// we probably want to query the lab test events and determine how many patients are still active
		List<Pair<String, PatientEvent>> events = new ArrayList<>();
		for (Patient patient : patients) {
			patient.getInformation().stream()
					.filter(info -> info.getZip() != null)
					.max(Comparator.comparing(PatientInformation::getCreationTimestamp)) // newest
					.ifPresent(patientInformation ->
							events.add(ImmutablePair.of(
									patientInformation.getZip(),
									patientEventRepository.findFirstByPatientOrderByEventTimestampDesc(patient)
							))
					);
		}

		final Map<AggregationResultZip, Long> aggregated = events.stream().collect(
				Collectors.groupingBy(el ->
								new AggregationResultZip().Zip(el.getLeft()).eventType(el.getRight().getPatientEventType().name()),
						Collectors.counting()));
		return aggregated.entrySet().stream().map(el -> el.getKey()
				.patientCount(el.getValue())).collect(Collectors.toList());
	}

}
