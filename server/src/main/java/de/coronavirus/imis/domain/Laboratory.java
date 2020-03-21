package de.coronavirus.imis.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

// FIXME: warning: Generating equals/hashCode implementation but without a call to superclass,
//  even though this class does not extend java.lang.Object.
//  If this is intentional, add '@EqualsAndHashCode(callSuper=false)' to your type.

@Entity
@Data
public class Laboratory extends Institution {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "laboratory")
    private List<LabTest> assignedLabTest;


}
