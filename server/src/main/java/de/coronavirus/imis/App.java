package de.coronavirus.imis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = "de.coronavirus.imis.api")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
