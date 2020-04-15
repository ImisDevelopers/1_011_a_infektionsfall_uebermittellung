package de.coronavirus.imis.services;

import de.coronavirus.imis.api.dto.AggregationResultZip;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.repositories.PatientEventRepository;
import de.coronavirus.imis.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		final List<Patient> patientIds = patientRepository.
				findAllByZipBetween(lowerBounds, upperBounds);
		var events = patientIds.stream().map(patient ->
				ImmutablePair.of(patient.getZip(), patientEventRepository.findFirstByPatientOrderByEventTimestampDesc(patient)))
				.collect(Collectors.toList());

		final Map<AggregationResultZip, Long> aggregated = events.stream().collect(
				Collectors.groupingBy(el ->
								new AggregationResultZip().Zip(el.getLeft()).eventType(el.right.getPatientEventType().name()),
						Collectors.counting()));
		return aggregated.entrySet().stream().map(el -> el.getKey()
				.patientCount(el.getValue())).collect(Collectors.toList());
	}

}
