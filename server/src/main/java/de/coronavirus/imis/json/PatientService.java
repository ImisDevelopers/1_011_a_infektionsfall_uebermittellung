package de.coronavirus.imis.json;

import de.coronavirus.imis.domain.Patient;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {
    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Iterable<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }
}
