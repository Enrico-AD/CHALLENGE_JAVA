package br.com.galpha.configuration;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class AdditionalConfigs {

    @Bean
    public FlywayMigrationStrategy repairFlyway() {
        return flyway -> {
            flyway.repair();
            flyway.migrate();
        };
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().info(
                new Info().title("SpringShop API").description("Spring shop sample application").version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))).externalDocs(
                new ExternalDocumentation().description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }
}
