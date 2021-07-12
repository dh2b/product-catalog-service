package com.philips.productcatalog.core.autoconfigure.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * Configuration class for user info service properties.
 */
@Data
@Configuration
@Validated
@ConfigurationProperties(prefix = "supply-chain-service")
public class SupplyChainConfigurationProperties {

    @NotBlank(message = "Base URL is required")
    private String baseUrl;

    @NotBlank(message = "Post endpoint is required")
    private String baseSupplyEndpoint;

    @NotBlank(message = "Delete endpoint is required")
    private String deleteSupplyEndpoint;

}
