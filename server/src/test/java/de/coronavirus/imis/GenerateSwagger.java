package de.coronavirus.imis;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class GenerateSwagger {

    @Autowired
    MockMvc mvc;

    @Test
    void generateSwagger() throws Exception {
        var result = mvc.perform(get("/v2/api-docs"))
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        System.out.print(result.getResponse().getContentAsString());
        Files.write(Paths.get("build/resources/swagger.json"),
                List.of(result.getResponse().getContentAsString()));
    }

}
