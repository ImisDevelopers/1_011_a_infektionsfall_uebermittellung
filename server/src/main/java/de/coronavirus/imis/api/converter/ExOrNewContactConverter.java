package de.coronavirus.imis.api.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.coronavirus.imis.api.dto.CreatePatientDTO;
import de.coronavirus.imis.services.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

/**
 * Allow exposure contacts to be specified either by ID to
 * an existing patient, or data for a new Patient instance
 */
@Service
@RequiredArgsConstructor
public class ExOrNewContactConverter {

  private final PatientService patientService;

  private final ObjectMapper jsonMapper = new ObjectMapper();

  public String convert(String contact) {
	  JsonNode in;
	  try {
		  in = jsonMapper.readTree(contact);
	  } catch (JsonProcessingException e) {
		  return contact;
	  }
	  if (in == null) {
      return null;
    } else if (in.isTextual()) {
      return in.textValue();
    } else {
      try {
        CreatePatientDTO newPatientDto = this.jsonMapper.treeToValue(in, CreatePatientDTO.class);
        return patientService.addPatient(newPatientDto, true).getId();
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
