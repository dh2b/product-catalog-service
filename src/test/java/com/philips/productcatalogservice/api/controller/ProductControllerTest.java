package com.philips.productcatalogservice.api.controller;

import com.philips.productcatalog.api.controller.ProductController;
import com.philips.productcatalog.api.dto.product.ProductResponseDto;
import com.philips.productcatalog.domain.mapping.ProductMapper;
import com.philips.productcatalog.domain.service.ProductService;
import com.philips.productcatalogservice.helper.ProductHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Product controller test class.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductController productController;

    /**
     * Find product by id should successful return the product.
     */
    @Test
    public void findProductByIdShouldReturnOkStatusWhenProductIsFound() {
        var product = ProductHelper.buildProduct();
        Mockito.when(productService.findById(ProductHelper.ID)).thenReturn(product);
        Mockito.when(productMapper.productToProductResponseDto(product)).thenReturn(ProductHelper.buildProductResponseDto());
        final ProductResponseDto productResponseDto = productController.getProductById(ProductHelper.ID);

        Assert.assertEquals(ProductHelper.NAME, productResponseDto.getName());
        Assert.assertEquals(ProductHelper.QUANTITY, productResponseDto.getQuantity());
        Assert.assertEquals(ProductHelper.PRICE, productResponseDto.getPrice());
    }

    /**
     * Find all method successful return status when product list is returned.
     */
    @Test
    public void findAllShouldReturnOkStatusWhenProductListIsReturned() {
        var product = ProductHelper.buildProduct();
        var productResponseDto = ProductHelper.buildProductResponseDto();
        var productList = Collections.singletonList(product);
        var productResponseDtos = Collections.singletonList(productResponseDto);
        Mockito.when(productService.findAll()).thenReturn(productList);
        Mockito.when(productMapper.productsToProductResponseDtoList(productList)).thenReturn(productResponseDtos);
        final List<ProductResponseDto> productResponseDtoList = productController.findAllProducts();
        Assert.assertEquals(ProductHelper.PRODUCT_LIST_ELEMENTS_NUMBER, productResponseDtoList.size());
    }

    /**
     * Add product should successful return status when product is saved.
     */
    @Test
    public void addProductShouldReturnOkStatusWhenProductIsSaved() {
        var productRequestDto = ProductHelper.buildProductRequestDto();
        var productResponseDto = ProductHelper.buildProductResponseDto();
        var product = ProductHelper.buildProduct();
        Mockito.when(productService.save(productRequestDto)).thenReturn(product);
        Mockito.when(productMapper.productToProductResponseDto(product)).thenReturn(productResponseDto);
        var response = productController.addProduct(productRequestDto);

        Assert.assertEquals(productRequestDto.getName(), response.getName());
        Assert.assertEquals(productRequestDto.getPrice(), response.getPrice());
        Assert.assertEquals(productRequestDto.getQuantity(), response.getQuantity());
    }

    /**
     * Delete product should successful return status when product is deleted.
     */
    @Test
    public void deleteProductShouldReturnOkStatusWhenProductIsDeleted() {
        Mockito.doNothing().when(productService).deleteById(ProductHelper.ID);
        productController.deleteProductById(ProductHelper.ID);

        var inOrder = Mockito.inOrder(productService);
        inOrder.verify(productService).deleteById(ProductHelper.ID);
    }

    /**
     * Update product should successful return status when product is updated.
     */
    @Test
    public void updatedProductShouldReturnOkStatusWhenProductIsUpdated() {
        var fields = new HashMap<String, Object>();
        fields.put("name", ProductHelper.NAME);
        fields.put("quantity", ProductHelper.QUANTITY);
        var product = ProductHelper.buildProduct();
        var productResponseDto = ProductHelper.buildProductResponseDto();
        Mockito.when(productService.updateById(ProductHelper.ID, fields)).thenReturn(product);
        Mockito.when(productMapper.productToProductResponseDto(product)).thenReturn(productResponseDto);
        var response = productController.updateProduct(ProductHelper.ID, fields);

        Assert.assertEquals(fields.get("name"), response.getName());
        Assert.assertEquals(fields.get("quantity"), response.getQuantity());
    }


}
