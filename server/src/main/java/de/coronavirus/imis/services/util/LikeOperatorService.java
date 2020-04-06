package de.coronavirus.imis.services.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LikeOperatorService {

    /**
     * Checks null and returns the searchParam as a like parameter
     * @param searchParam Search Param
     * @return like operator
     */
    public String likeOperatorOrEmptyString(final String searchParam) {
        if (searchParam == null) {
            return "%%";
        } else {
            return "%" + searchParam + "%";
        }
    }

}
