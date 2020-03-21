package de.coronavirus.imis.file_storage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.coronavirus.imis.domain.TestReport;


public class FileStorageDataService {

    public void addTestReport(Long testID, String filePath){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory('');
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );

        TestReport testReport = new TestReport();
        testReport.setID(testID);
        testReport.setFilePath(filePath);

        entitymanager.persist( testReport );
        entitymanager.getTransaction( ).commit( );
        entitymanager.close( );
        emfactory.close( );
    }

    public TestReport getTestReport(Long testID){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory('');
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );

        TestReport testReport = entitymanager.find(TestReport.id, testID);
        entitymanager.close( );
        emfactory.close( );

        return(testReport);
    }

    public void updateTestReport(Long testID, String filePath){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory('');
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );

        TestReport testReport = entitymanager.find(TestReport.id, testID);
        testReport.setFilePath(filePath);
        entitymanager.getTransaction( ).commit( );

        entitymanager.close( );
        emfactory.close( );
    }

    public void deleteTestReport(Long testID){
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory('');
        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );

        TestReport testReport = entitymanager.find(TestReport.id, testID);
        entitymanager.remove(testReport);

        entitymanager.getTransaction( ).commit( );
        entitymanager.close( );
        emfactory.close( );
    }
}
