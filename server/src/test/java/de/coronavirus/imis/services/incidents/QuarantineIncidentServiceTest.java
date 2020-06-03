package de.coronavirus.imis.services.incidents;

import de.coronavirus.imis.domain.EventType;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.domain.QuarantineIncident;
import de.coronavirus.imis.repositories.QuarantineIncidentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class QuarantineIncidentServiceTest {

  private QuarantineIncidentService quarantineIncidentService;

  private CommonIncidentService mockedCommonIncidentService;
  private QuarantineIncidentRepository mockedQuarantineIncidentRepository;

  @BeforeEach
  public void setup() {
    mockedCommonIncidentService = Mockito.mock(CommonIncidentService.class);
    mockedQuarantineIncidentRepository = Mockito.mock(QuarantineIncidentRepository.class);

    quarantineIncidentService = new QuarantineIncidentService(mockedCommonIncidentService, mockedQuarantineIncidentRepository);
  }

  @Test
  public void testHasActiveQuarantine() {

    var patient = new Patient();
    patient.setId("id");

    var incident = new QuarantineIncident();
    incident.setEventType(EventType.QUARANTINE_MANDATED);
    incident.setUntil(LocalDate.now().plusDays(1));

    when(mockedQuarantineIncidentRepository.findByPatientId(patient.getId()))
      .thenReturn(Collections.singletonList(incident));

    boolean hasActiveQuarantine = quarantineIncidentService.hasActiveQuarantine(patient);

    assertThat(hasActiveQuarantine).isFalse();

  }

  @Test
  public void testHasInactiveQuarantine() {

    var patient = new Patient();
    patient.setId("id");

    var incident = new QuarantineIncident();
    incident.setEventType(EventType.QUARANTINE_MANDATED);
    incident.setUntil(LocalDate.now().minusDays(1));

    when(mockedQuarantineIncidentRepository.findByPatientId(patient.getId()))
      .thenReturn(Collections.singletonList(incident));

    boolean hasActiveQuarantine = quarantineIncidentService.hasActiveQuarantine(patient);

    assertThat(hasActiveQuarantine).isTrue();

  }

}