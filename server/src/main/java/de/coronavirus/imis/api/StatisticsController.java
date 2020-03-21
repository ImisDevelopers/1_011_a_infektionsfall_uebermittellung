package de.coronavirus.imis.api;

import de.coronavirus.imis.services.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
@Slf4j
public class StatisticsController {
    private final PatientService patientService;

    @GetMapping("/zip")
    public List<Object[]> getCasesByZip() {
        log.info(patientService.getCasesGroupedZip().toString());
        return patientService.getCasesGroupedZip();
    }
}
