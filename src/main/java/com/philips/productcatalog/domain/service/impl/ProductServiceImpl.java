package com.philips.productcatalog.domain.service.impl;

import com.philips.productcatalog.api.dto.product.ProductRequestDto;
import com.philips.productcatalog.domain.exception.ProductNotFoundException;
import com.philips.productcatalog.domain.mapping.ProductMapper;
import com.philips.productcatalog.domain.service.DownstreamSyncService;
import com.philips.productcatalog.domain.service.ProductService;
import com.philips.productcatalog.infrastructure.model.Product;
import com.philips.productcatalog.infrastructure.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Implementation of product service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final DownstreamSyncService downstreamSyncService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Product save(ProductRequestDto productRequestDto) {
        log.info("Adding product {}", productRequestDto);
        var product = productMapper.productRequestDtoToProduct(productRequestDto);
        var productSaved = productRepository.save(product);
        downstreamSyncService.addProductSync(productSaved);
        log.info("Product {} added", productSaved);
        return productSaved;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product findById(String id) {
        log.info("Finding product by id {}", id);
        var product = productRepository.findOneById(id).orElseThrow(ProductNotFoundException::new);
        log.info("Product found `{}", product);
        return product;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> findAll() {
        log.info("Finding all the available products");
        var products = productRepository.findAll();
        log.info("Products found: {}", products.size());
        return products;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(String id) {
        log.info("Deleting product by id {}", id);
        var product = productRepository.findOneById(id).orElseThrow(ProductNotFoundException::new);
        downstreamSyncService.deleteProductSync(product);
        productRepository.deleteById(product.getId());
        log.info("Product with id {} was deleted", product.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Product updateById(String id, Map<String, Object> updateFields) {
        var product = productRepository.findOneById(id).orElseThrow(ProductNotFoundException::new);
        updateFields.forEach(
                (change, value) -> {
                    switch (change) {
                        case "quantity":
                            product.setQuantity((Integer) value);
                            break;
                        case "price":
                            product.setPrice((Integer) value);
                            break;
                        case "name":
                            product.setName((String) value);
                            break;
                        default:
                            break;
                    }
                });
        downstreamSyncService.addProductSync(product);
        return productRepository.save(product);
    }
}
