package com.philips.productcatalog.core.autoconfigure.webclient;

import com.philips.productcatalog.core.autoconfigure.properties.SupplyChainConfigurationProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 * WebClient configuration for the Supply Chain requests.
 */
@Configuration
@RequiredArgsConstructor
public class SupplyChainWebClientConfiguration extends ExchangeFilterHelper {

    private final SupplyChainConfigurationProperties supplyChainConfigurationProperties;

    private static final String SERVICE = "SUPPLY_CHAIN";

    /**
     * Webclient configuration bean.
     * @param readTimeout max read timeout in seconds
     * @param connectTimeout max connect timeout in milliseconds.
     * @return webclient bean configured
     */
    @Bean(name = "supplyChainWebClient")
    public WebClient supplyChainWebClient(@Value("${supplyChainService.read-timeout-sec}") Integer readTimeout,
                                          @Value("${supplyChainService.connect-timeout-ms}") Integer connectTimeout) {
        var connector = new ReactorClientHttpConnector(
                HttpClient.create().tcpConfiguration(client ->
                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
                                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(readTimeout))))
        );

        return WebClient.builder()
                .clientConnector(connector)
                .baseUrl(supplyChainConfigurationProperties.getBaseUrl())
                .filter(logRequest(SERVICE))
                .filter(logResponseStatus(SERVICE))
                .build();
    }


}
