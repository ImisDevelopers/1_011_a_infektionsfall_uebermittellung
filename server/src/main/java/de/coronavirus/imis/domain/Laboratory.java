package de.coronavirus.imis.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
public class Laboratory extends InstitutionImpl {


    @OneToMany(mappedBy = "laboratory")
    private List<LabTest> assignedLabTest;


    @Override
    public InstitutionType getType() {
        return InstitutionType.LABORATORY;
    }
}
