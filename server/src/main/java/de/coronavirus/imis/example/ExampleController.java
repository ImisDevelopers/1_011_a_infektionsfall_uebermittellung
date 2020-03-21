package de.coronavirus.imis.example;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    private ExampleService exampleService;

    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @GetMapping(path = "/exampleEntities")
    public ResponseEntity<List<ExampleEntity>> getExampleEntities() {
        return ResponseEntity.ok(exampleService.getExampleEntities());
    }

    @PostMapping("/exampleEntities")
    public ResponseEntity<ExampleEntity> createExampleEntity(@RequestBody ExampleEntity exampleEntity) {
        return ResponseEntity.ok(exampleService.createExampleEntity(exampleEntity));
    }
}
