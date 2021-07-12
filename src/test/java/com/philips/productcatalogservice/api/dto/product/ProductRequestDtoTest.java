package com.philips.productcatalogservice.api.dto.product;

import com.philips.productcatalog.api.dto.product.ProductRequestDto;
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
 * Product request data transfer object test.
 */
public class ProductRequestDtoTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    private ProductRequestDto productRequestDto;
    private Set<ConstraintViolation<ProductRequestDto>> violations;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        productRequestDto = ProductHelper.buildProductRequestDto();
    }

    /**
     * Product request data transfer object should not have blank name attribute.
     */
    @Test
    public void productRequestDtoShouldNotHaveBlankNameAttribute() {
        productRequestDto.setName(null);
        violations = validator.validate(productRequestDto);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Product request data transfer object should not have null price attribute.
     */
    @Test
    public void productRequestDtoShouldNotHaveNullPriceAttribute() {
        productRequestDto.setPrice(null);
        violations = validator.validate(productRequestDto);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Product request data transfer object should not have null quantity attribute.
     */
    @Test
    public void productRequestDtoShouldNotHaveNullQuantityAttribute() {
        productRequestDto.setQuantity(null);
        violations = validator.validate(productRequestDto);
        Assert.assertFalse(violations.isEmpty());
    }
}
