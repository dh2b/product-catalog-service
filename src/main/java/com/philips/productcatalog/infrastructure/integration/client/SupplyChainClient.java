package com.philips.productcatalog.infrastructure.integration.client;

import com.philips.productcatalog.api.dto.product.ProductRequestDto;
import com.philips.productcatalog.api.dto.product.ProductResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface SupplyChainClient {

    /**
     * Fetch all the products from external service.
     * @return {@link List} of {@link ProductResponseDto} product response data transfer object
     */
    Optional<List<ProductResponseDto>> getAllProducts();

    /**
     * Add a product for the external services.
     * @param productRequestDto product request data transfer object
     * @return  {@link Optional} of {@link ProductResponseDto} product response data transfer object
     */
    Optional<ProductResponseDto> addProduct(ProductRequestDto productRequestDto);

    /**
     * Delete a product by id from the external services.
     * @param id id of the product to be deleted
     * @return {@link ResponseEntity}
     */
    Optional<ResponseEntity<Void>> deleteProduct(String id);
}
