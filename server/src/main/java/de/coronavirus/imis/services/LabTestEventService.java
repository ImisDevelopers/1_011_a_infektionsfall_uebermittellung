package de.coronavirus.imis.services;

import de.coronavirus.imis.domain.LabTest;
import de.coronavirus.imis.domain.LabTestEvent;
import de.coronavirus.imis.domain.LabTestEventType;
import de.coronavirus.imis.repositories.LabTestEventRepository;
import de.coronavirus.imis.repositories.LabTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class LabTestEventService {

	private final AuthService authService;
	private final LabTestEventRepository labTestEventRepository;
	private final LabTestRepository labTestRepository;

	public void createLabTestEvent(LabTest labTest, LabTestEventType eventType, OffsetDateTime eventTimestamp, String comment) {
		labTest.setTestStatus(eventType);
		labTestRepository.save(labTest);
		LabTestEvent event = new LabTestEvent()
				.setCreationTimestamp(OffsetDateTime.now())
				.setEventTimestamp(eventTimestamp)
				.setLabTest(labTest)
				.setUser(authService.getCurrentUser())
				.setLabTestEventType(eventType)
				.setComment(comment)
				.setLabTest(labTest);
		labTestEventRepository.saveAndFlush(event);
	}

}
