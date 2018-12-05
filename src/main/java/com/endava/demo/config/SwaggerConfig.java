package com.endava.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * When the application is up and running, open "http://localhost:8085/program/swagger-ui.html"
 * to access Swagger UI at your local environment.
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final static String API_BASE_PACKAGE = "com.endava.demo.controller";

    /**
     * Created the bean, carrying swagger setup settings.
     *
     * @return Docket with all the settings in it
     */
    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(metaData())
                .select()
                .apis(RequestHandlerSelectors.basePackage(API_BASE_PACKAGE))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * API base information.
     * Adds additional information to the swagger.
     *
     * @return ApiInfoBuilder object, containing additional information for the documentation
     */
    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Spring Boot REST API")
                .description("Spring Boot REST API for Testing Project")
                .version("v1.0")
                .contact(new Contact("Blagovest Anev", "https://www.linkedin.com/in/blagovestanev/", "blagovestanev@gmail.com"))
                .build();
    }
}