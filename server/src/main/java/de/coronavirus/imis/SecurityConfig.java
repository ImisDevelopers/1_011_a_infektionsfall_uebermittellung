package de.coronavirus.imis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//Ensures redirect from http -> https
@Configuration
@Profile("production")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment environment;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.portMapper()
                .http(Integer.parseInt(environment.getProperty("server.http.port", "8080"))) // http port defined in yml config file
                .mapsTo(Integer.parseInt(environment.getProperty("server.port", "443"))); // https port defined in yml config file

        http.requiresChannel().anyRequest().requiresSecure();
    }
}
