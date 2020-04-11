package de.coronavirus.imis.config;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.DELETE;

import de.coronavirus.imis.domain.Institution;
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

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenFilter filter;

    private final String CLINIC = InstitutionType.CLINIC.name();
    private final String DEPARTMENT_OF_HEALTH = InstitutionType.DEPARTMENT_OF_HEALTH.name();
    private final String DOCTORS_OFFICE = InstitutionType.DOCTORS_OFFICE.name();
    private final String GOVERNMENT_AGENCY = InstitutionType.GOVERNMENT_AGENCY.name();
    private final String LABORATORY = InstitutionType.LABORATORY.name();
    private final String TEST_SITE = InstitutionType.TEST_SITE.name();


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // FIXME route permissions
        //@formatter:off
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // OPTIONS matcher for CORS, do not delete
                .antMatchers(OPTIONS, "/**").permitAll()
                .antMatchers(POST, "/patients").permitAll()
                .antMatchers("/patients").hasAnyRole(DEPARTMENT_OF_HEALTH, CLINIC, DOCTORS_OFFICE, TEST_SITE)
                .antMatchers("/doctor/*").hasAnyRole(DEPARTMENT_OF_HEALTH, DOCTORS_OFFICE, CLINIC)
                .mvcMatchers(POST, "/labtest").hasAnyRole(DEPARTMENT_OF_HEALTH, DOCTORS_OFFICE, CLINIC, TEST_SITE)
                .antMatchers("/labtest/patient/*").hasAnyRole(DEPARTMENT_OF_HEALTH, DOCTORS_OFFICE, CLINIC, TEST_SITE)
                .mvcMatchers(PUT, "/labtest/*").hasAnyRole(DEPARTMENT_OF_HEALTH, LABORATORY)
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
    }


}
