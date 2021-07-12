package com.philips.productcatalog.domain.service;

import com.philips.productcatalog.api.dto.product.ProductRequestDto;
import com.philips.productcatalog.infrastructure.model.Product;

import java.util.List;
import java.util.Map;

/**
 * Product service interface.
 */
public interface ProductService {

    /**
     * Saves a product.
     *
     * @param productRequestDto {@link ProductRequestDto} product request data transfer object
     * @return {@link Product} product model
     */
    Product save(ProductRequestDto productRequestDto);

    /**
     * Searches product by its id.
     *
     * @param id to be used to search the product
     * @return {@link Product} product model
     */
    Product findById(String id);

    /**
     * Retrieves all products.
     *
     * @return {@link List} of {@link Product} product model
     */
    List<Product> findAll();

    /**
     * Delete a product by id.
     *
     * @param id product id to be deleted
     */
    void deleteById(String id);

    /**
     * Updates a product.
     *
     * @param id product id to be updated
     * @param updateFields {@link Map<>} fields to change
     * @return {@link Product} product model
     */
    Product updateById(String id, Map<String, Object> updateFields);

}
