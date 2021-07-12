package com.philips.productcatalog.domain.exception;

/**
 * External service exception.
 */
public class ExternalServiceException extends RuntimeException {

    private final int statusCode;

    /**
     * Exception for external service response.
     * @param message error message
     * @param statusCode error status code
     */
    public ExternalServiceException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
