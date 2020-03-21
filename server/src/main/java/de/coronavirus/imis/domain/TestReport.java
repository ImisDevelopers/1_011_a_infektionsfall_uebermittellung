package de.coronavirus.imis.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;





@Entity
@Table(name = "test_report")
public class TestReport {
    @Id
    public Long id;

    public String filePath;

    public void setId(Long id){
        this.id = id;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

}

