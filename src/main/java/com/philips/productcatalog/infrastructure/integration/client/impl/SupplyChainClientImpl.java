package com.philips.productcatalog.infrastructure.integration.client.impl;

import com.philips.productcatalog.api.dto.product.ProductRequestDto;
import com.philips.productcatalog.api.dto.product.ProductResponseDto;
import com.philips.productcatalog.core.autoconfigure.properties.SupplyChainConfigurationProperties;
import com.philips.productcatalog.domain.exception.ExternalServiceException;
import com.philips.productcatalog.infrastructure.integration.client.SupplyChainClient;
import com.philips.productcatalog.infrastructure.integration.dto.SupplyChainResponseDto;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * Supply chain client implementation class.
 */
@Slf4j
@Service
@Data
public class SupplyChainClientImpl implements SupplyChainClient {

    private static final int MAX_ATTEMPTS = 3;
    private static final int DURATION_SEC = 2;

    private final WebClient supplyChainWebClient;
    private final SupplyChainConfigurationProperties supplyChainConfigurationProperties;

    /**
     * Default constructor.
     * @param supplyChainWebClient webclient bean
     * @param supplyChainConfigurationProperties configuration properties
     */
    public SupplyChainClientImpl(@Qualifier("supplyChainWebClient") WebClient supplyChainWebClient,
                                 SupplyChainConfigurationProperties supplyChainConfigurationProperties) {
        this.supplyChainWebClient = supplyChainWebClient;
        this.supplyChainConfigurationProperties = supplyChainConfigurationProperties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<ProductResponseDto>> getAllProducts() {
        log.info("Fetch all the products from Supply Chain Service");
        try {
            var serviceResponse = Optional.ofNullable(getSupplyChainWebClient()
                    .get()
                    .uri(supplyChainConfigurationProperties.getBaseSupplyEndpoint())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.empty())
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> Mono.error(new ExternalServiceException("Server error", response.rawStatusCode())))
                    .bodyToMono(SupplyChainResponseDto.class)
                    .onErrorResume(WebClientResponseException.NotFound.class, notFound -> Mono.empty())
                    .retryWhen(Retry.fixedDelay(MAX_ATTEMPTS, Duration.ofSeconds(DURATION_SEC)).filter(ExternalServiceException.class::isInstance))
                    .block());
            return serviceResponse.map(SupplyChainResponseDto::getBundle);
        } catch (RuntimeException ex) {
            log.error("An error occurred", ex);
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ProductResponseDto> addProduct(ProductRequestDto productRequestDto) {
        log.info("Creating Product in the Supply Chain Service {}", productRequestDto);
        try {
            return Optional.ofNullable(getSupplyChainWebClient()
                    .post()
                    .uri(supplyChainConfigurationProperties.getBaseSupplyEndpoint())
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(productRequestDto)
                    .retrieve()
                    .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.empty())
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> Mono.error(new ExternalServiceException("Server error", response.rawStatusCode())))
                    .bodyToMono(ProductResponseDto.class)
                    .onErrorResume(WebClientResponseException.NotFound.class, notFound -> Mono.empty())
                    .retryWhen(Retry.fixedDelay(MAX_ATTEMPTS, Duration.ofSeconds(DURATION_SEC)).filter(ExternalServiceException.class::isInstance))
                    .block());
        } catch (RuntimeException ex) {
            log.error("An error occurred", ex);
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ResponseEntity<Void>> deleteProduct(String id) {
        log.info("Deleting Product in the Supply Chain Service {}", id);
        try {
            return Optional.ofNullable(getSupplyChainWebClient()
                    .delete()
                    .uri(uriBuilder -> uriBuilder.path(supplyChainConfigurationProperties.getDeleteSupplyEndpoint())
                            .build(id))
                    .retrieve()
                    .onStatus(HttpStatus.NOT_FOUND::equals, clientResponse -> Mono.empty())
                    .onStatus(HttpStatus::is5xxServerError,
                    response -> Mono.error(new ExternalServiceException("Server error", response.rawStatusCode())))
                    .toBodilessEntity()
                    .ignoreElement()
                    .onErrorResume(WebClientResponseException.NotFound.class, notFound -> Mono.empty())
                    .retryWhen(Retry.fixedDelay(MAX_ATTEMPTS, Duration.ofSeconds(DURATION_SEC)).filter(ExternalServiceException.class::isInstance))
                    .block());
        } catch (RuntimeException ex) {
            log.error("An error occurred", ex);
            return Optional.empty();
        }
    }
}
