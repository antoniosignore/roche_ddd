package com.roche.ddd.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Roche code test API")
            .description("Order Service")
            .license("MIT")
            .licenseUrl("http://www.signore.com")
            .termsOfServiceUrl("http://www.signore.com/ts")
            .version("1.0.0")
            .build();
    }

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.roche"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
    }
}
