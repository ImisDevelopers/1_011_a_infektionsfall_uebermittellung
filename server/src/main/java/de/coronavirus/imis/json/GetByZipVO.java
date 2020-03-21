package de.coronavirus.imis.json;

import java.util.Map;

import lombok.Getter;

@Getter
public class GetByZipVO {
    private final Map<String, Integer> cases;

    public GetByZipVO(Map<String, Integer> cases) {
        this.cases = cases;
    }
}
