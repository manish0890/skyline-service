package com.manish0890.skyline.service.swagger;

import com.google.common.base.Predicates;
import com.google.common.collect.Sets;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableAutoConfiguration
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfig {

    private String description;
    private String title;
    private String version;

    @Bean
    public Docket swaggerSpringMvcPluginForInformation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Information")
                .apiInfo(apiInfo())
                .tags(new Tag("Customer API", "Create, Read, and Update Skyline Customer API"), generateInformationTags())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))//<6>, regex must be in double quotes.
                .build()
                .produces(Sets.newHashSet(MediaType.APPLICATION_JSON.toString()))
                .consumes(Sets.newHashSet(MediaType.APPLICATION_JSON.toString()))
                .forCodeGeneration(true);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .version(version)
                .description(description)
                .title(title).build();
    }

    /**
     * Additional {@link Tag}s for Information Controllers - Customer Tag is created in Docket function.
     * @return {@link Tag} Array
     */
    private Tag[] generateInformationTags() {
        return new Tag[] {
                // new Tag("Rooms API", "Allocate, Vacant or Disable Skyline Room API")
        };
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
