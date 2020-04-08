package de.coronavirus.imis.api;

import java.util.List;

import de.coronavirus.imis.api.dto.PatientSearchParamsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.domain.Patient;
import de.coronavirus.imis.services.PatientService;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<Patient> addPatient(@RequestBody CreatePatientDTO dto) {
        var patient = patientService.addPatient(dto);
        if (patient == null) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok(patient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientForId(@PathVariable("id") String id) {
        return patientService.findPatientById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients(){
        return ResponseEntity.ok(patientService.getAllPatients());
    }


    @PostMapping("/query")
    public List<Patient> queryPatients(@RequestBody final PatientSearchParamsDTO patientSearchParamsDTO){
        return patientService.queryPatients(patientSearchParamsDTO);
    }

    @GetMapping("/query-simple")
    public List<Patient> queryPatientsSimple(@RequestParam String query) {
        return patientService.queryPatientsSimple(query);
    }

    @PostMapping("/query/count")
    public Long countQueryPatients(@RequestBody final PatientSearchParamsDTO patientSearchParamsDTO){
        return patientService.countQueryPatients(patientSearchParamsDTO);
    }
}
