package de.coronavirus.imis.example;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    private ExampleRepository exampleRepository;

    public ExampleService(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    public List<ExampleEntity> getExampleEntities() {
        return exampleRepository.findAll();
    }

    public ExampleEntity createExampleEntity(ExampleEntity exampleEntity) {
        return exampleRepository.save(exampleEntity);
    }
}
