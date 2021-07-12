package com.philips.productcatalog.domain.event;

import com.philips.productcatalog.infrastructure.model.Product;

public interface PublishEventService {
    /**
     * Publishes downstream sync errors event.
     * @param httpMethod HTTP method to be retried
     * @param product product entity
     */
    void publishErrorSyncEvent(String httpMethod, Product product);
}
