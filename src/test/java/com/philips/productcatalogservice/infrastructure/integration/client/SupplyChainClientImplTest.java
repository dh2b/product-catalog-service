package com.philips.productcatalogservice.infrastructure.integration.client;


import com.philips.productcatalog.core.autoconfigure.properties.SupplyChainConfigurationProperties;
import com.philips.productcatalog.infrastructure.integration.client.impl.SupplyChainClientImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Supply chain client implementation test.
 */
@RunWith(MockitoJUnitRunner.class)
public class SupplyChainClientImplTest {

    @Mock
    private WebClient supplyChainWebClient;

    @Mock
    private SupplyChainConfigurationProperties supplyChainConfigurationProperties;

    @InjectMocks
    private SupplyChainClientImpl supplyChainClient;


    /**
     * Find by idf should return product entity when product is found.
     */
    @Test
    public void getAllProductsShouldReturnProductListFromServices() {
        //TODO
        // Learn how make unit tests for Webclient external calls or include this tests in the integration tests.

    }
}
