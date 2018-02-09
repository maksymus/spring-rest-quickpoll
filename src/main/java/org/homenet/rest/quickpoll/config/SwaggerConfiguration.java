package org.homenet.rest.quickpoll.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// http://localhost:8080/v2/api-docs?group=v1
// http://localhost:8080/swagger-ui.html
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private ApiInfo apiInfo(String version) {
        return new ApiInfoBuilder()
                .title("Spring Rest QuickPoll API")
                .description("Spring Rest QuickPoll API reference for developers")
//                .termsOfServiceUrl("http://javainuse.com")
//                .license("MIT License")
//                .licenseUrl("mail@gmail.com")
                .version(version).build();
    }

    @Bean
    public Docket apiV1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("v1"))
                .groupName("v1")
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.regex(".*/api/v1/.*"))
                .build();
    }

    @Bean
    public Docket apiV2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("v2"))
                .groupName("v2")
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.regex(".*/api/v2/.*"))
                .build();
    }

}
