package de.coronavirus.imis;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;


@Component
public class TestDataLoader implements ApplicationRunner {
    static String readFile(String testFileName)
            throws IOException {

        Resource resource = new ClassPathResource(testFileName);

        InputStream input = resource.getInputStream();

        File file = resource.getFile();


        byte[] encoded = Files.readAllBytes(file.toPath());
        return new String(encoded, Charset.defaultCharset());
    }

    private final PatientService patientService;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public TestDataLoader(PatientService patientService) {
        this.patientService = patientService;
    }


    public void run(ApplicationArguments args) {
        // Generate patients
        try {
            CreatePatientDTO patientDTO = mapper.readValue(readFile("sample_data/createPerson.json"), CreatePatientDTO.class);
            patientService.addPatient(patientDTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}