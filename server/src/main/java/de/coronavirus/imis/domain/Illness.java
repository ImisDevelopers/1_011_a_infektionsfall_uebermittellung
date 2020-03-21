package de.coronavirus.imis.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;


@Entity
@Value
@JsonDeserialize(builder = Illness.IllnesBuilder.class)
@Builder
public class Illness {
    @Id
    private String icdCode;
    private String displayName;
    private String comment;


    @JsonPOJOBuilder(withPrefix = "")
    public static final class IllnesBuilder {

    }

}
