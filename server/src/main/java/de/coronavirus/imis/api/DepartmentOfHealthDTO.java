package de.coronavirus.imis.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentOfHealthDTO {
    String name;
    String type;
    String street;
    String city;
    String county;

    String phone;
    String fax;
    String email;
}
