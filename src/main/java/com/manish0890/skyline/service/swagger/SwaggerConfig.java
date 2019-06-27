package com.manish0890.skyline.service.swagger;

import com.google.common.collect.Sets;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableAutoConfiguration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfig {

    @Bean
    public Docket swaggerSpringMvcPluginForInformation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Information")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .produces(Sets.newHashSet(MediaType.APPLICATION_JSON.toString()))
                .consumes(Sets.newHashSet(MediaType.APPLICATION_JSON.toString()))
                .forCodeGeneration(true);
    }
}
