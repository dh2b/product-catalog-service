package com.philips.productcatalogservice.helper;

import com.philips.productcatalog.api.dto.product.ProductRequestDto;
import com.philips.productcatalog.api.dto.product.ProductResponseDto;
import com.philips.productcatalog.infrastructure.model.Product;

/**
 * Product helper class.
 */
public final class ProductHelper {

    public static final Integer PRICE = 1000;
    public static final Integer QUANTITY = 3;
    public static final String NAME = "Product name";
    public static final String ID = "8e4dff32-c383-4f1c-9a32-c8f86f9847e2";
    public static final int PRODUCT_LIST_ELEMENTS_NUMBER = 1;
    /**
     * Builds a new instance of Product.
     *
     * @return new instance of {@link Product}
     */
    public static Product buildProduct() {
        return new Product(QUANTITY, PRICE, NAME);
    }

    /**
     * Builds a new instance of product request data transfer object.
     *
     * @return new instance of {@link ProductRequestDto}
     */
    public static ProductRequestDto buildProductRequestDto() {
        var productRequestDto = new ProductRequestDto();
        productRequestDto.setPrice(PRICE);
        productRequestDto.setQuantity(QUANTITY);
        productRequestDto.setName(NAME);
        return productRequestDto;
    }

    /**
     * Builds a new instance of product response data transfer object.
     *
     * @return new instance of {@link ProductResponseDto}
     */
    public static ProductResponseDto buildProductResponseDto() {
        var productResponseDto = new ProductResponseDto();
        productResponseDto.setPrice(PRICE);
        productResponseDto.setQuantity(QUANTITY);
        productResponseDto.setName(NAME);
        return productResponseDto;
    }
}
