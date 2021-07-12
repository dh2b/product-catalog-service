package com.philips.productcatalog.domain.event;

import com.philips.productcatalog.infrastructure.model.Product;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Downstream sync error event.
 */
@Data
public class SyncErrorEvent {

    @NotBlank(message = "Version is required.")
    private final String version;

    @NotBlank(message = "Http Method is required")
    private final String httpMethod;

    @NotNull(message = "Product is required")
    private final Product product;

    /**
     * Class constructor.
     *
     * @param httpMethod http method to be retry
     * @param product    product entity
     */
    public SyncErrorEvent(String httpMethod, Product product) {
        this.version = UUID.randomUUID().toString();
        this.httpMethod = httpMethod;
        this.product = product;
    }
}
