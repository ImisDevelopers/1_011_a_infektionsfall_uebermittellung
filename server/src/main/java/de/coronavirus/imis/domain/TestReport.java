package de.coronavirus.imis.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;


@Entity
@Data
public class TestReport {
    @Id
    public Long id;

    public String filePath;


    public void setID(Long id){
        this.id = id;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

}

