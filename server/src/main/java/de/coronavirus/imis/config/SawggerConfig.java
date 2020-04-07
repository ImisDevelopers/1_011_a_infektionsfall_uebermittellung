package de.coronavirus.imis.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SawggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
            .ignoredParameterTypes(AuthenticationPrincipal.class)
            .securityContexts(Lists.newArrayList(securityContext()))
            .securitySchemes(Lists.newArrayList(apiKey()))
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build();
  }

  private ApiKey apiKey() {
    return new ApiKey("JWT", "Authorization", "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex("/.*"))
            .build();
  }

  List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope
            = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Lists.newArrayList(
            new SecurityReference("JWT", authorizationScopes));
  }

}
