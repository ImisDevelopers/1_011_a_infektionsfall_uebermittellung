package de.coronavirus.imis.services;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/***
 * Implements functionality to save files related to a test to the database.
 */
@Service
public class TestReportService {

    @Autowired
    private Storage storage = StorageOptions.getDefaultInstance().getService();
    private Bucket bucket = storage.create(BucketInfo.of("test_reports"));

    /***
     * Returns all saved test reports.
     * @return all saved test reports.
     */
    public List<Blob> getAllTestReports() {
        List<Blob> result = new ArrayList<>();
        Page<Blob> blobs = bucket.list();

        for (Blob blob: blobs.getValues()) {
            result.add(blob);
        }

        return result;
    }

    /***
     * Returns a test report by its id from the database.
     * @param id: The id of the desired test report.
     * @return the desired test report.
     */
    public Blob findTestReportById(String id) {
        return storage.get(BlobId.of(bucket.getName(), id));
    }

    /***
     * Creates a test report and saves it in the database.
     * @param id: Id of the test report.
     * @param file: Binary data of the test report.
     */
    public void addTestReport(String id, byte[] file) {
        BlobId blobId = BlobId.of(bucket.getName(), id);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, file);
    }
}
