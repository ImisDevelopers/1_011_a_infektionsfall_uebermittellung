package de.coronavirus.imis.example;

import java.util.List;

import de.coronavirus.imis.domain.TestReport;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStorageDataRepository extends CrudRepository<TestReport, Long> {


    void delete(TestReport testReport);

    @Override
    TestReport findById(Long id);

}
