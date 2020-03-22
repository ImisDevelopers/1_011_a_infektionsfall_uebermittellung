package de.coronavirus.imis.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Laboratory extends Institution {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "laboratory")
    private List<LabTest> assignedLabTest;


}
