package de.coronavirus.imis.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import de.coronavirus.imis.api.dto.AggregationResultZip;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.repositories.PatientEventRepository;
import de.coronavirus.imis.repositories.PatientRepository;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final PatientRepository patientRepository;
    private final PatientEventRepository patientEventRepository;

    @Transactional(readOnly = true)
    public List<AggregationResultZip> resultZipList(Integer lowerBounds, Integer upperBounds) {
        final List<Patient> patientIds = patientRepository.
                findAllByZipBetween(lowerBounds, upperBounds);
        var events = patientIds.stream().map(patient ->
                ImmutablePair.of(patient.getZip(), patientEventRepository.findFirstByPatientOrderByEventTimestampDesc(patient)))
                .collect(Collectors.toList());

        final Map<AggregationResultZip, Long> aggregated = events.stream().collect(
                Collectors.groupingBy(el ->
                                new AggregationResultZip().Zip(el.getLeft()).eventType(el.right.getEventType().name()),
                        Collectors.counting()));
        return aggregated.entrySet().stream().map(el -> el.getKey()
                .patientCount(el.getValue())).collect(Collectors.toList());
    }

}
