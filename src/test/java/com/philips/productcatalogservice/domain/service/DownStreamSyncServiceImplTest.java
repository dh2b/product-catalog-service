package com.philips.productcatalogservice.domain.service;

import com.philips.productcatalog.domain.event.PublishEventService;
import com.philips.productcatalog.domain.mapping.ProductMapper;
import com.philips.productcatalog.domain.service.impl.DownStreamSyncServiceImpl;
import com.philips.productcatalog.infrastructure.integration.client.SupplyChainClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static com.philips.productcatalogservice.helper.ProductHelper.*;

/**
 * Down Stream sync service implementation test.
 */
@RunWith(MockitoJUnitRunner.class)
public class DownStreamSyncServiceImplTest {

    @Mock
    private SupplyChainClient supplyChainClient;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private PublishEventService publishEventService;

    @InjectMocks
    private DownStreamSyncServiceImpl downStreamSyncServiceImpl;


    /**
     * Add Product sync should successfully propagate the new product to the downstream services.
     */
    @Test
    public void addProductShouldPostNewProductForDownStreamServices() {
        var product = buildProduct();
        var productRequestDto = buildProductRequestDto();
        var productResponseDto = buildProductResponseDto();
        Mockito.when(productMapper.productToProductRequestDto(product)).thenReturn(productRequestDto);
        Mockito.when(supplyChainClient.addProduct(productRequestDto)).thenReturn(Optional.of(productResponseDto));
        downStreamSyncServiceImpl.addProductSync(product);

        var inOrder = Mockito.inOrder(productMapper, supplyChainClient);
        inOrder.verify(productMapper).productToProductRequestDto(product);
        inOrder.verify(supplyChainClient).addProduct(productRequestDto);
    }

    /**
     * Add Product sync should post event if find any exception for propagate the new product to the downstream services.
     */
    @Test
    public void saveShouldPostEventWhenDownStreamServiceIsDown() {
        var product = buildProduct();
        var productRequestDto = buildProductRequestDto();
        Mockito.when(productMapper.productToProductRequestDto(product)).thenReturn(productRequestDto);
        Mockito.when(supplyChainClient.addProduct(productRequestDto)).thenReturn(Optional.empty());
        Mockito.doNothing().when(publishEventService).publishErrorSyncEvent(HttpMethod.POST.name(), product);
        downStreamSyncServiceImpl.addProductSync(product);

        var inOrder = Mockito.inOrder(productMapper, supplyChainClient, publishEventService);
        inOrder.verify(productMapper).productToProductRequestDto(product);
        inOrder.verify(supplyChainClient).addProduct(productRequestDto);
        inOrder.verify(publishEventService).publishErrorSyncEvent(HttpMethod.POST.name(), product);
    }

    /**
     * Delete Product sync should successfully delete the product to the downstream services.
     */
    @Test
    public void deleteProductShouldDeleteProductForDownStreamServices() {
        var product = buildProduct();
        var productRequestDto = buildProductRequestDto();
        var productResponseDto = buildProductResponseDto();
        Mockito.when(supplyChainClient.deleteProduct(product.getId())).thenReturn(Optional.of(ResponseEntity.noContent().build()));
        downStreamSyncServiceImpl.deleteProductSync(product);

        var inOrder = Mockito.inOrder(productMapper, supplyChainClient);
        inOrder.verify(supplyChainClient).deleteProduct(product.getId());
    }

    /**
     * Delete Product sync should post event if find any exception when deleting the product to the downstream services.
     */
    @Test
    public void deleteShouldPostEventWhenDownStreamServiceIsDown() {
        var product = buildProduct();
        Mockito.when(supplyChainClient.deleteProduct(product.getId())).thenReturn(Optional.empty());
        Mockito.doNothing().when(publishEventService).publishErrorSyncEvent(HttpMethod.DELETE.name(), product);
        downStreamSyncServiceImpl.deleteProductSync(product);

        var inOrder = Mockito.inOrder(supplyChainClient, publishEventService);
        inOrder.verify(supplyChainClient).deleteProduct(product.getId());
        inOrder.verify(publishEventService).publishErrorSyncEvent(HttpMethod.DELETE.name(), product);
    }

    /**
     * Get All Products sync should return product list from the downstream services.
     */
    @Test
    public void getAllProductShouldReturnProductListFromExternalServices() {
        var products = List.of(buildProductResponseDto());
        Mockito.when(supplyChainClient.getAllProducts()).thenReturn(Optional.of(products));
        var allProductsSync = downStreamSyncServiceImpl.getAllProductsSync();
        Assert.assertEquals(PRODUCT_LIST_ELEMENTS_NUMBER, allProductsSync.size());
    }
}
