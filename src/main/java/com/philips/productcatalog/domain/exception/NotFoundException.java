package com.philips.productcatalog.domain.exception;

/**
 * Not found exception.
 */
public abstract class NotFoundException extends RuntimeException {

    /**
     * Class default constructor.
     *
     * @param message error message
     */
    protected NotFoundException(String message) {
        super(message);
    }
}
