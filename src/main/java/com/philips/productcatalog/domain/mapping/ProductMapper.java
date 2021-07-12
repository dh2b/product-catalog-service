package com.philips.productcatalog.domain.mapping;

import com.philips.productcatalog.api.dto.product.ProductRequestDto;
import com.philips.productcatalog.api.dto.product.ProductResponseDto;
import com.philips.productcatalog.infrastructure.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Product mapper.
 */
@Mapper
public interface ProductMapper {

    /**
     * Maps product entity from product request data transfer object.
     *
     * @param productRequestDto product request data transfer object
     * @return {@link Product} product entity
     */
    @Mapping(target = "id", ignore = true)
    Product productRequestDtoToProduct(ProductRequestDto productRequestDto);

    /**
     * Maps product entity from product request data transfer object.
     *
     * @param product product entity
     * @return {@link ProductRequestDto} product request data transfer object
     */
    ProductRequestDto productToProductRequestDto(Product product);

    /**
     * Maps product response data transfer object from product entity.
     *
     * @param product product entity
     * @return {@link ProductResponseDto} product response data transfer object
     */
    ProductResponseDto productToProductResponseDto(Product product);

    /**
     * Maps product response data transfer object list from product entity list.
     *
     * @param products product entity list
     * @return {@link List} of {@link ProductResponseDto} product response data transfer object
     */
    List<ProductResponseDto> productsToProductResponseDtoList(List<Product> products);

}
