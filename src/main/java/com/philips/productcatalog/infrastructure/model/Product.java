package com.philips.productcatalog.infrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Product entity.
 */
@Data
@Document
@Validated
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseModel {

    @NotNull(message = "Quantity is required.")
    private Integer quantity;

    @NotNull(message = "Price is required.")
    private Integer price;

    @NotBlank(message = "Name is required.")
    private String name;
}
