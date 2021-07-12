package com.philips.productcatalog.api.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Product request data transfer object.
 */
@Schema(description = "Product request data transfer object.")
@Data
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductRequestDto {

    private String id;

    @NotNull(message = "Quantity is required.")
    @Schema(required = true, description = "quantity")
    private Integer quantity;

    @NotNull(message = "Price is required.")
    @Schema(required = true, description = "price")
    private Integer price;

    @NotBlank(message = "Name is required.")
    @Schema(required = true, description = "name")
    private String name;
}
