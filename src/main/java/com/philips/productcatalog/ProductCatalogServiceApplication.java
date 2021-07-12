package com.philips.productcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * SpringBoot application class.
 */
@EnableMongoRepositories(basePackages = "com.philips.productcatalog.infrastructure.repository")
@SpringBootApplication
public class ProductCatalogServiceApplication {

    /**
     * Application main method.
     *
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplication.run(ProductCatalogServiceApplication.class, args);
    }
}
