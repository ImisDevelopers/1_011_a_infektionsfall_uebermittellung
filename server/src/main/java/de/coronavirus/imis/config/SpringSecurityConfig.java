package de.coronavirus.imis.config;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.DELETE;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

import de.coronavirus.imis.config.domain.UserRole;
import de.coronavirus.imis.domain.InstitutionType;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenFilter filter;

    private final List<String> allowedOrigins = Arrays.asList(new String[]{
            "https://imis-prototyp.de",
            "https://staging.imis-prototyp.de",
            "http://imis-prototyp.de",
            "http://localhost:8080",
    });

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsFilter corsFilter() {
        CorsFilter filter = new CorsFilter();
        return filter;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //@formatter:off
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/patients").hasAnyRole(InstitutionType.CLINIC.name(), InstitutionType.DOCTORS_OFFICE.name(), InstitutionType.TEST_SITE.name())
                .antMatchers("/doctor/*").hasAnyRole(InstitutionType.DOCTORS_OFFICE.name(), InstitutionType.CLINIC.name())
                .mvcMatchers(POST, "/labtest").hasAnyRole(InstitutionType.DOCTORS_OFFICE.name(), InstitutionType.CLINIC.name(), InstitutionType.TEST_SITE.name())
                .antMatchers("/labtest/patient/*").hasAnyRole(InstitutionType.DOCTORS_OFFICE.name(), InstitutionType.CLINIC.name(), InstitutionType.TEST_SITE.name())
                .mvcMatchers(PUT, "/labtest/*").hasRole(InstitutionType.LABORATORY.name())
                .antMatchers("/stats").authenticated()
                .antMatchers("/auth").permitAll()
                .antMatchers("/auth/register").hasAuthority(UserRole.USER_ROLE_ADMIN.name())
                .antMatchers("/institutions/*").hasAuthority(UserRole.USER_ROLE_ADMIN.name())
                .antMatchers(
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**" ,
                        /*Probably not needed*/ "/swagger.json").permitAll()

                .antMatchers("/**").permitAll();
        //@formatter:on
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(corsFilter(), SessionManagementFilter.class);

     // TODO set strict rules
        http.cors().configurationSource(request -> {
            var config = new CorsConfiguration().applyPermitDefaultValues();
//            config.addAllowedHeader("Authorization");
//            config.addAllowedMethod(POST);
//            config.addAllowedMethod(GET);
//            config.addAllowedMethod(OPTIONS);
//            config.addAllowedMethod(DELETE);
//            config.addAllowedMethod(PUT);
            config.setAllowedOrigins(allowedOrigins); // using with applyPermitDefaultValues not working
            return config;
        });
    }


}
