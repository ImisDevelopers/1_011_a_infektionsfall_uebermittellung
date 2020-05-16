package de.coronavirus.imis.api;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import de.coronavirus.imis.api.dto.ExposureContactDTO;
import de.coronavirus.imis.mapper.ExposureContactMapper;
import de.coronavirus.imis.repositories.ExposureContactRepository;
import de.coronavirus.imis.services.util.BulkOperationService;
import static de.coronavirus.imis.services.util.BulkOperationService.*;


@RestController
@RequestMapping("/api/exposure-contacts")
@RequiredArgsConstructor
public class ExposureContactController {
  private final ExposureContactRepository exposureContactRepository;
  private final ExposureContactMapper exposureContactMapper;

  private final BulkOperationService bulkOps;

  @PostMapping
  public ExposureContactDTO.FromServer createExposureContact(@RequestBody ExposureContactDTO.ToServer dto) {
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
      .stream().map(contact -> exposureContactMapper.toExposureContactDTO(contact))
      .collect(Collectors.toList());
  }
  @GetMapping("/by-contact/{id}")
  public List<ExposureContactDTO.FromServer> getExposureSourceContactsForPatient(@PathVariable("id") String patientId) {
    return exposureContactRepository.findByContactId(patientId)
      .stream().map(contact -> exposureContactMapper.toExposureContactDTO(contact))
      .collect(Collectors.toList());
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
