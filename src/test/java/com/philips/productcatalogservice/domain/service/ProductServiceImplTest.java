package com.philips.productcatalogservice.domain.service;

import com.philips.productcatalog.domain.exception.ProductNotFoundException;
import com.philips.productcatalog.domain.mapping.ProductMapper;
import com.philips.productcatalog.domain.service.DownstreamSyncService;
import com.philips.productcatalog.domain.service.impl.ProductServiceImpl;
import com.philips.productcatalog.infrastructure.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.philips.productcatalogservice.helper.ProductHelper.*;

/**
 * Product service implementation test.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private DownstreamSyncService downstreamSyncService;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    /**
     * Save should return product entity when product is saved.
     */
    @Test
    public void saveShouldReturnProductEntityWhenProductIsSaved() {
        var product = buildProduct();
        var productRequestDto = buildProductRequestDto();
        Mockito.when(productMapper.productRequestDtoToProduct(productRequestDto)).thenReturn(product);
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Mockito.doNothing().when(downstreamSyncService).addProductSync(product);
        var productResponse = productServiceImpl.save(productRequestDto);

        Assert.assertEquals(NAME, productResponse.getName());
        Assert.assertEquals(QUANTITY, productResponse.getQuantity());
        Assert.assertEquals(PRICE, productResponse.getPrice());

        var inOrder = Mockito.inOrder(downstreamSyncService);
        inOrder.verify(downstreamSyncService).addProductSync(product);
    }


    /**
     * Find by idf should return product entity when product is found.
     */
    @Test
    public void findByIdShouldReturnProductEntityWhenProductIsFound() {
        var product = buildProduct();
        Mockito.when(productRepository.findOneById(ID)).thenReturn(Optional.of(product));
        var productResponse = productServiceImpl.findById(ID);

        Assert.assertEquals(NAME, productResponse.getName());
        Assert.assertEquals(QUANTITY, productResponse.getQuantity());
        Assert.assertEquals(PRICE, productResponse.getPrice());
    }

    /**
     * Find by id should throw product not found exception when product is not found.
     */
    @Test(expected = ProductNotFoundException.class)
    public void findByIdShouldThrowProductNotFoundExceptionWhenProductIsNotFound() {
        productServiceImpl.findById(ID);
    }

    /**
     * Find all should return product list when any product is found.
     */
    @Test
    public void findAllShouldReturnProductListWhenAnyProductIsFound() {
        var products = List.of(buildProduct());
        Mockito.when(productRepository.findAll()).thenReturn(products);
        var productResponseList = productServiceImpl.findAll();
        Assert.assertEquals(PRODUCT_LIST_ELEMENTS_NUMBER, productResponseList.size());
    }

    /**
     * Delete should return nothing when the product is deleted.
     */
    @Test
    public void deleteShouldReturnNothingWhenProductIsDeleted() {
        var product = buildProduct();
        Mockito.when(productRepository.findOneById(ID)).thenReturn(Optional.of(product));
        Mockito.doNothing().when(downstreamSyncService).deleteProductSync(product);
        Mockito.doNothing().when(productRepository).deleteById(product.getId());
        productServiceImpl.deleteById(ID);

        var inOrder = Mockito.inOrder(productRepository, downstreamSyncService, productRepository);
        inOrder.verify(productRepository).findOneById(ID);
        inOrder.verify(downstreamSyncService).deleteProductSync(product);
        inOrder.verify(productRepository).deleteById(product.getId());
    }

    /**
     * Update should return the product entity updated.
     */
    @Test
    public void updateShouldReturnProductEntityWhenProductIsUpdated() {
        var product = buildProduct();
        var fields = new HashMap<String, Object>();
        fields.put("name", "new name");
        fields.put("quantity", 0);
        Mockito.when(productRepository.findOneById(ID)).thenReturn(Optional.of(product));
        Mockito.doNothing().when(downstreamSyncService).addProductSync(product);
        Mockito.when(productRepository.save(product)).thenReturn(product);
        var updatedProduct = productServiceImpl.updateById(ID, fields);

        Assert.assertEquals("new name", updatedProduct.getName());
        Assert.assertEquals(Integer.valueOf(0), updatedProduct.getQuantity());

        var inOrder = Mockito.inOrder(productRepository, downstreamSyncService, productRepository);
        inOrder.verify(productRepository).findOneById(ID);
        inOrder.verify(downstreamSyncService).addProductSync(product);
        inOrder.verify(productRepository).save(product);
    }
}
