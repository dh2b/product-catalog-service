package com.philips.productcatalog.infrastructure.repository;

import com.philips.productcatalog.infrastructure.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Product repository.
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    /**
     * Checks if a product is found by name.
     *
     * @param name product name
     * @return {@code true} if product is found by name, {@code false} otherwise
     */
    boolean existsByName(String name);

    /**
     * Finds product by id.
     *
     * @param id id
     * @return {@link Optional} of {@link Product}
     */
    Optional<Product> findOneById(String id);
}
