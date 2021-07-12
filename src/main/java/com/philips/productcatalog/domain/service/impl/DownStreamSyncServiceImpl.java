package com.philips.productcatalog.domain.service.impl;

import com.philips.productcatalog.api.dto.product.ProductResponseDto;
import com.philips.productcatalog.domain.event.PublishEventService;
import com.philips.productcatalog.domain.mapping.ProductMapper;
import com.philips.productcatalog.domain.service.DownstreamSyncService;
import com.philips.productcatalog.infrastructure.integration.client.SupplyChainClient;
import com.philips.productcatalog.infrastructure.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of downstream sync service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DownStreamSyncServiceImpl implements DownstreamSyncService {

    private final SupplyChainClient supplyChainClient;
    private final ProductMapper productMapper;
    private final PublishEventService publishEventService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addProductSync(Product product) {
        log.info("Adding product {} to the downstream services", product);
        var productRequestDto = productMapper.productToProductRequestDto(product);
        var isSyncSuccessful = supplyChainClient.addProduct(productRequestDto).isPresent();
        if (!isSyncSuccessful) {
            publishEventService.publishErrorSyncEvent(HttpMethod.POST.name(), product);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteProductSync(Product product) {
        log.info("Deleting product {} to the downstream services", product);
        var isSyncSuccessful = supplyChainClient.deleteProduct(product.getId()).isPresent();
        if (!isSyncSuccessful) {
            publishEventService.publishErrorSyncEvent(HttpMethod.DELETE.name(), product);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductResponseDto> getAllProductsSync() {
        log.info("Fetching all products from downstream services");
        return supplyChainClient.getAllProducts().orElseGet(ArrayList::new);
    }
}
