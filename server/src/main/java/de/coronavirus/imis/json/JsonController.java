package de.coronavirus.imis.json;

import de.coronavirus.imis.domain.Patient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class JsonController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();


    private PatientService patientService;

    public JsonController(PatientService patientService) {
        this.patientService = patientService;
    }


    @ResponseBody
    @GetMapping("/json")
    public InfectionSet sayHello(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
        return new InfectionSet(counter.incrementAndGet(), String.format(template, name));
    }

    @ResponseBody
    @GetMapping("/zip")
    public GetByZipVO returnCasesByZip() {
        // TODO: Get patients by zip and sum
        return new GetByZipVO(new HashMap<>());
    }

    @ResponseBody
    @GetMapping("/json/patients")
    public ResponseEntity<Iterable<Patient>> getPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @ResponseBody
    @GetMapping("/json/patients/{id}")
    public ResponseEntity<Patient> getPatients(@PathVariable("id") String id) {
        try {
            var patient = patientService.getPatientById(Long.valueOf(id));
            if (patient.isPresent()) {
                return ResponseEntity.ok(patient.get());
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }
}
