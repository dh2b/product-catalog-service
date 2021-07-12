package com.philips.productcatalog.api.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Product response data transfer object.
 */
@Schema(description = "Product response data transfer object.")
@Data
@Validated
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponseDto {

    @NotBlank(message = "Id is required.")
    @Schema(required = true, description = "Id")
    private String id;

    @NotNull(message = "Quantity is required.")
    @Schema(required = true, description = "Quantity")
    private Integer quantity;

    @NotNull(message = "Price is required.")
    @Schema(required = true, description = "Price")
    private Integer price;

    @NotBlank(message = "Name is required.")
    @Schema(required = true, description = "Product name")
    private String name;

    private LocalDateTime creationDateTime;
}
