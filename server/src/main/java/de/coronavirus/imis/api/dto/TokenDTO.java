package de.coronavirus.imis.api.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TokenDTO {
    private final String jwtToken;
}
