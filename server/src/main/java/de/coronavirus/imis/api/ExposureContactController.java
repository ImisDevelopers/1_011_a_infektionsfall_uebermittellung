package de.coronavirus.imis.api;

import de.coronavirus.imis.api.dto.ExposureContactDTO;
import de.coronavirus.imis.mapper.ExposureContactMapper;
import de.coronavirus.imis.repositories.ExposureContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/exposure-contacts")
@RequiredArgsConstructor
public class ExposureContactController {
	private final ExposureContactRepository exposureContactRepository;
	private final ExposureContactMapper exposureContactMapper;

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
				.stream().map(exposureContactMapper::toExposureContactDTO)
				.collect(Collectors.toList());
	}

	@GetMapping("/by-contact/{id}")
	public List<ExposureContactDTO.FromServer> getExposureSourceContactsForPatient(@PathVariable("id") String patientId) {
		return exposureContactRepository.findByContactId(patientId)
				.stream().map(exposureContactMapper::toExposureContactDTO)
				.collect(Collectors.toList());
	}

	@DeleteMapping("/{id}")
	public void removeExposureContact(@PathVariable("id") long id) {
		this.exposureContactRepository.deleteById(id);
	}
}
