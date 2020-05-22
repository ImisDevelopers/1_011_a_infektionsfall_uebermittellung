package de.coronavirus.imis.api;

import java.util.*;
import java.util.stream.Collectors;

import de.coronavirus.imis.api.converter.ExOrNewContactConverter;
import de.coronavirus.imis.api.dto.ExposureContactDTO;
import de.coronavirus.imis.mapper.ExposureContactMapper;
import de.coronavirus.imis.repositories.ExposureContactRepository;
import de.coronavirus.imis.services.util.BulkOperationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static de.coronavirus.imis.services.util.BulkOperationService.BulkRequest;
import static de.coronavirus.imis.services.util.BulkOperationService.ItemStatus;


@RestController
@RequestMapping("/api/exposure-contacts")
@RequiredArgsConstructor
public class ExposureContactController {
  private final ExposureContactRepository exposureContactRepository;
  private final ExposureContactMapper exposureContactMapper;
  private final ExOrNewContactConverter exOrNewContactConverter;

  private final BulkOperationService bulkOps;

  @PostMapping
  public ExposureContactDTO.FromServer createExposureContact(@RequestBody ExposureContactDTO.ToServer dto) {
    dto.setContact(exOrNewContactConverter.convert(dto.getContact()));
    return exposureContactMapper.toExposureContactDTO(
      exposureContactRepository.saveAndFlush(
        exposureContactMapper.toExposureContact(dto)
      )
    );
  }

  @PutMapping
  public ExposureContactDTO.FromServer updateExposureContact(@RequestBody ExposureContactDTO.ToServer contact) {
    return exposureContactMapper.toExposureContactDTO(
      exposureContactRepository.saveAndFlush(
        exposureContactMapper.toExposureContact(contact)
      )
    );
  }

  @GetMapping("/{id}")
  public ExposureContactDTO.FromServer getExposureContact(@PathVariable("id") long id) {
    return exposureContactMapper.toExposureContactDTO(exposureContactRepository.findById(id).orElse(null));
  }

  @GetMapping("/by-source/{id}")
  public List<ExposureContactDTO.FromServer> getExposureContactsForPatient(@PathVariable("id") String patientId) {
    return exposureContactRepository.findBySourceId(patientId)
      .stream().map(exposureContactMapper::toExposureContactDTO)
      .collect(Collectors.toList());
  }

  @GetMapping("/by-contact/{id}")
  public List<ExposureContactDTO.FromServer> getExposureSourceContactsForPatient(@PathVariable("id") String patientId) {
    return exposureContactRepository.findByContactId(patientId)
      .stream().map(exposureContactMapper::toExposureContactDTO)
      .collect(Collectors.toList());
  }

  @PostMapping("/by-contact/")
	public Map<String, List<ExposureContactDTO.FromServer>> getExposureSourceContactsForPatients(@RequestBody List<String> patientIds)
	{
		var result = new HashMap<String, List<ExposureContactDTO.FromServer>>();
		for (String patientId : patientIds)
			result.put(patientId, getExposureSourceContactsForPatient(patientId));
		return result;
	}

	@DeleteMapping("/{id}")
  public void removeExposureContact(@PathVariable("id") long id) {
    this.exposureContactRepository.deleteById(id);
  }

  @PostMapping("/bulk")
  public List<ItemStatus<ExposureContactDTO.FromServer, String, OpResult>> bulkInsert(@RequestBody BulkRequest<BulkInsertOptions, ExposureContactDTO.ToServer> req) {
    return bulkOps.performBulkOperation(req, (var item, var options) -> {
      OpResult op = OpResult.CREATE;
      try {
        var exposureContact = this.exposureContactMapper.toExposureContact(item);
        var inDb = exposureContactRepository.findBySourceIdAndContactId(
          exposureContact.getSource().getId(), exposureContact.getContact().getId());

        if (inDb.isPresent()) {
          // Override entry
          op = OpResult.OVERRIDE;
          if (!options.isAllowOverride()) throw new RuntimeException("Overriding not allowed by option");
          exposureContact.setId(inDb.get().getId());
        }

        var result = exposureContactMapper.toExposureContactDTO(
          exposureContactRepository.saveAndFlush(exposureContact));

        return new ItemStatus()
          .setResult(result, op);

      } catch (Exception e) {
        return new ItemStatus()
          .setError(e.getMessage(), op);
      }
    });
  }

  @Data
  public static class BulkInsertOptions {
    private boolean allowOverride;
  }

  public enum OpResult {
    CREATE,
    OVERRIDE,
  }
}
