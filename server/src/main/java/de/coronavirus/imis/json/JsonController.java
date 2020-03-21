package de.coronavirus.imis.json;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class JsonController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @ResponseBody
    @GetMapping("/json")
    public InfectionSet sayHello(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
        return new InfectionSet(counter.incrementAndGet(), String.format(template, name));
    }
}
