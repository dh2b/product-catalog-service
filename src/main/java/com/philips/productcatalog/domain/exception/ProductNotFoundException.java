package com.philips.productcatalog.domain.exception;

/**
 * Not found exception.
 */
public class ProductNotFoundException extends NotFoundException {

    /**
     * Class default constructor.
     */
    public ProductNotFoundException() {
        super("Product not found");
    }
}
