package com.philips.productcatalog.core.autoconfigure.webclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

/**
 * Exchange filter helper class for logs.
 */
@Slf4j
@Component
public class ExchangeFilterHelper {

    /**
     * Log the Webclient request.
     * @param service name of the service to be log.
     * @return filter for logs
     */
    public ExchangeFilterFunction logRequest(String service) {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            log.info("[{}] Request: {} {} {}", service, request.method(), request.url(), request.body());
            return Mono.just(request);
        });
    }

    /**
     * Log the Webclient response status.
     * @param service name of the service to be log.
     * @return filter for logs
     */
    public ExchangeFilterFunction logResponseStatus(String service) {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            log.info("[{}] Response Status: {}", service, response.statusCode());
            return Mono.just(response);
        });
    }
}
