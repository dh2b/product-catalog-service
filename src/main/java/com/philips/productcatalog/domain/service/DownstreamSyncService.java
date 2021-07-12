package com.philips.productcatalog.domain.service;

import com.philips.productcatalog.api.dto.product.ProductResponseDto;
import com.philips.productcatalog.infrastructure.model.Product;

import java.util.List;

public interface DownstreamSyncService {

    /**
     * Service to add a new product to the external services.
     * @param product product to be added
     */
    void addProductSync(Product product);

    /**
     * Service to delete a product to the external services.
     * @param product product to be deleted
     */
    void deleteProductSync(Product product);

    /**
     * Fetch all the products from external service.
     * @return {@link List} of {@link ProductResponseDto} product response data transfer object
     */
    List<ProductResponseDto> getAllProductsSync();

}
