package de.coronavirus.imis.services;


import de.coronavirus.imis.api.DepartmentOfHealthDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ZipToDepartmentOfHealthService {
    private final RestTemplate restTemplate;

    public ZipToDepartmentOfHealthService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * Get the corresponding department of health
     * @param zip postal code
     * @return Department of health
     */
    public DepartmentOfHealthDTO getDepartmentOfHealth(String zip) {
        String url = String.format("https://tools.rki.de/PLZTool/?q=%s", zip);
        // Parse HTML
        String resultHTML = this.restTemplate.getForObject(url, String.class);
        log.info(resultHTML);
        assert resultHTML != null;
        int nameStart = resultHTML.indexOf("<strong>", resultHTML.indexOf("<address>")) + 8;
        int nameEnd = resultHTML.indexOf("</strong>", nameStart);
        String name = resultHTML.substring(nameStart, nameEnd).trim();
        log.info(name);
        int typeStart = resultHTML.indexOf("<br />", nameEnd) + 6;
        int typeEnd = resultHTML.indexOf("<br />", typeStart);
        String type = resultHTML.substring(typeStart, typeEnd).trim();
        log.info(type);
        int streetStart = typeEnd + 6;
        int streetEnd = resultHTML.indexOf("<br />", typeStart);
        String street = resultHTML.substring(streetStart, streetEnd).trim();
        log.info(type);
        int cityStart = typeEnd + 6;
        int cityEnd = resultHTML.indexOf("<br />", cityStart);
        String city = resultHTML.substring(cityStart, cityEnd).trim();
        log.info(city);
        int countyStart = typeEnd + 6;
        int countyEnd = resultHTML.indexOf("</address>", countyStart);
        String county = resultHTML.substring(countyStart, countyEnd).trim();
        log.info(county);
        int phoneStart = resultHTML.indexOf("value", resultHTML.indexOf("Telefon", countyEnd)) + 7;
        int phoneEnd = resultHTML.indexOf("\"", phoneStart);
        String phone = resultHTML.substring(phoneStart, phoneStart).trim();
        log.info(phone);
        int faxStart = resultHTML.indexOf("value", resultHTML.indexOf("Fax", phoneEnd)) + 7;
        int faxEnd = resultHTML.indexOf("\"", faxStart);
        String fax = resultHTML.substring(faxStart, faxEnd).trim();
        log.info(fax);
        int emailStart = resultHTML.indexOf("value", resultHTML.indexOf("E-Mail", faxEnd)) + 7;
        int emailEnd = resultHTML.indexOf("\"", emailStart);
        String email = resultHTML.substring(emailStart, emailEnd).trim();
        log.info(email);
        return new DepartmentOfHealthDTO(name, type, street, city, county, phone, fax, email);
    }
}
