package de.coronavirus.imis.example;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepository extends CrudRepository<ExampleEntity, Long> {

    @Override
    List<ExampleEntity> findAll();
}
