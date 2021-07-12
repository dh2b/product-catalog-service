package com.philips.productcatalogservice.infrastructure.model;

import com.philips.productcatalog.infrastructure.model.Product;
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
 * Product test.
 */
public class ProductTest {

    private final  ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final  Validator validator = validatorFactory.getValidator();

    private Product product;
    private Set<ConstraintViolation<Product>> violations;

    /**
     * Method setup.
     */
    @Before
    public void setup() {
        product = ProductHelper.buildProduct();
    }

    /**
     * Product should not have blank name attribute.
     */
    @Test
    public void productShouldNotHaveBlankNameAttribute() {
        product.setName("");
        violations = validator.validate(product);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Product should not have null price attribute.
     */
    @Test
    public void productShouldNotHaveNullPriceAttribute() {
        product.setPrice(null);
        violations = validator.validate(product);
        Assert.assertFalse(violations.isEmpty());
    }

    /**
     * Product should not have null quantity attribute.
     */
    @Test
    public void productShouldNotHaveNullQuantityAttribute() {
        product.setQuantity(null);
        violations = validator.validate(product);
        Assert.assertFalse(violations.isEmpty());
    }
}
