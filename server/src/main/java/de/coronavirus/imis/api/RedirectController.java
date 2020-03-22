package de.coronavirus.imis.api;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedirectController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void method(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Location", "/swagger-ui.html");
        httpServletResponse.setStatus(302);
    }
}
