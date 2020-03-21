package de.coronavirus.imis.api;

import de.coronavirus.imis.services.ZipToDepartmentOfHealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentOfHealthController {
    private final ZipToDepartmentOfHealthService zipToDepartmentOfHealthService;

    @GetMapping("/{zip}")
    public ResponseEntity<DepartmentOfHealthDTO> getDepartmentOfHealthForZip(@PathVariable("zip") String zip) {
        return new ResponseEntity<>(zipToDepartmentOfHealthService.getDepartmentOfHealth(zip), HttpStatus.OK);
    }
}
