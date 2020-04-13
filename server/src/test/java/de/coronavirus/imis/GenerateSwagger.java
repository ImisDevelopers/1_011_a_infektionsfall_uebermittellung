package de.coronavirus.imis;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class GenerateSwagger {

    @Autowired
    MockMvc mvc;

    @Test
    void generateSwagger() throws Exception {
        var result = mvc.perform(MockMvcRequestBuilders.get("/v2/api-docs"))
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).andReturn();
        System.out.print(result.getResponse().getContentAsString());
        Files.deleteIfExists(Paths.get("build/resources/swagger.json"));
        Files.write(Paths.get("build/resources/swagger.json"), List.of(result.getResponse().getContentAsString()));
        Assertions.assertThat(Files.readAllLines(Path.of("build/resources/swagger.json"), Charset.defaultCharset()))
                .hasSizeGreaterThan(0);
    }

}
