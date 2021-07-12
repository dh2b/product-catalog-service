package com.philips.productcatalog.infrastructure.integration.dto;

import com.philips.productcatalog.api.dto.product.ProductResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class SupplyChainResponseDto {

    private List<ProductResponseDto> bundle;
}
