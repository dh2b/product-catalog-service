package com.philips.productcatalogservice.api.dto.product;

import com.philips.productcatalog.api.dto.product.ProductResponseDto;
import com.philips.productcatalogservice.helper.ProductHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Product response data transfer object test.
 */
public class ProductResponseDtoTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    private ProductResponseDto productResponseDto;
    private Set<ConstraintViolation<ProductResponseDto>> violations;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        productResponseDto = ProductHelper.buildProductResponseDto();
    }

    /**
     * Product response data transfer object should not have blank id attribute.
     */
    @Test
    public void productResponseDtoShouldNotHaveBlankIdAttribute() {
        productResponseDto.setId("");
        violations = validator.validate(productResponseDto);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Product response data transfer object should not have null quantity attribute.
     */
    @Test
    public void productResponseDtoShouldNotHaveNullQuantityAttribute() {
        productResponseDto.setQuantity(null);
        violations = validator.validate(productResponseDto);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Product response data transfer object should not have null price attribute.
     */
    @Test
    public void productResponseDtoShouldNotHaveNullPriceAttribute() {
        productResponseDto.setPrice(null);
        violations = validator.validate(productResponseDto);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Product response data transfer object should not have blank name attribute.
     */
    @Test
    public void productResponseDtoShouldNotHaveBlankNameAttribute() {
        productResponseDto.setName("");
        violations = validator.validate(productResponseDto);
        Assert.assertFalse(violations.isEmpty());
    }
}
