package com.philips.productcatalog.api.controller;

import com.philips.productcatalog.api.constants.HttpStatusDescriptionConstants;
import com.philips.productcatalog.api.dto.product.ProductRequestDto;
import com.philips.productcatalog.api.dto.product.ProductResponseDto;
import com.philips.productcatalog.api.dto.error.RestErrorResponseDto;
import com.philips.productcatalog.domain.mapping.ProductMapper;
import com.philips.productcatalog.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

import static org.springdoc.core.Constants.*;

/**
 * Product controller.
 */
@Tag(name = "Product Controller")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController extends BaseController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    /**
     * Searches product by its id.
     *
     * @param id to be used to search the product
     * @return {@link ProductResponseDto} product response data transfer object
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = GET_METHOD, summary = "Find product by id")
    @Parameter(in = ParameterIn.PATH, name = "id", required = true, example = "26809cf3-3687-42f5-9ed0-1d7e0e2e81ef")
    @ApiResponse(
            responseCode = HttpStatusDescriptionConstants.SUCCESS_REQUEST_CODE,
            description = HttpStatusDescriptionConstants.SUCCESS_REQUEST_DESCRIPTION,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductResponseDto.class))
    )
    @ApiResponse(
            responseCode = HttpStatusDescriptionConstants.BAD_REQUEST_CODE,
            description = HttpStatusDescriptionConstants.BAD_REQUEST_DESCRIPTION,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @ApiResponse(
            responseCode = HttpStatusDescriptionConstants.NOT_FOUND_REQUEST_CODE,
            description = HttpStatusDescriptionConstants.NOT_FOUND_REQUEST_DESCRIPTION,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @GetMapping(value = "/product/{id}")
    public ProductResponseDto getProductById(@NotBlank(message = "Id is required") @PathVariable("id") final String id) {
        var product = productService.findById(id);
        return productMapper.productToProductResponseDto(product);
    }

    /**
     * Retrieves all products.
     *
     * @return {@link List} of {@link ProductResponseDto} product response data transfer object
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = GET_METHOD, summary = "List all the products")
    @ApiResponse(
            responseCode = HttpStatusDescriptionConstants.SUCCESS_REQUEST_CODE,
            description = HttpStatusDescriptionConstants.SUCCESS_REQUEST_DESCRIPTION,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ProductResponseDto.class)))
    )
    @ApiResponse(
            responseCode = HttpStatusDescriptionConstants.BAD_REQUEST_CODE,
            description = HttpStatusDescriptionConstants.BAD_REQUEST_DESCRIPTION,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @GetMapping(value = "/products")
    public List<ProductResponseDto> findAllProducts() {
        var productList = productService.findAll();
        return productMapper.productsToProductResponseDtoList(productList);
    }

    /**
     * Add a product.
     *
     * @param productRequestDto {@link ProductRequestDto} product request data transfer object
     * @return {@link ProductResponseDto} product response data transfer object
     */
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(method = POST_METHOD, summary = "Add a product in the database")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Product request data transfer object",
            required = true,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductRequestDto.class))
    )
    @ApiResponse(
            responseCode = HttpStatusDescriptionConstants.CREATED_REQUEST_CODE,
            description = HttpStatusDescriptionConstants.SUCCESS_REQUEST_DESCRIPTION,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductResponseDto.class))
    )
    @ApiResponse(
            responseCode = HttpStatusDescriptionConstants.BAD_REQUEST_CODE,
            description = HttpStatusDescriptionConstants.BAD_REQUEST_DESCRIPTION,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @PostMapping(value = "/product")
    public ProductResponseDto addProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        var productSaved = productService.save(productRequestDto);
        return productMapper.productToProductResponseDto(productSaved);
    }

    /**
     * Delete a product by its id.
     *
     * @param id to be used to delete the product
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(method = DELETE_METHOD, summary = "Delete product by id")
    @Parameter(in = ParameterIn.PATH, name = "id", required = true, example = "26809cf3-3687-42f5-9ed0-1d7e0e2e81ef")
    @ApiResponse(
            responseCode = HttpStatusDescriptionConstants.NO_CONTENT_REQUEST_CODE,
            description = HttpStatusDescriptionConstants.SUCCESS_REQUEST_DESCRIPTION
    )
    @ApiResponse(
            responseCode = HttpStatusDescriptionConstants.BAD_REQUEST_CODE,
            description = HttpStatusDescriptionConstants.BAD_REQUEST_DESCRIPTION,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @ApiResponse(
            responseCode = HttpStatusDescriptionConstants.NOT_FOUND_REQUEST_CODE,
            description = HttpStatusDescriptionConstants.NOT_FOUND_REQUEST_DESCRIPTION,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @DeleteMapping(value = "/product/{id}")
    public void deleteProductById(@NotBlank(message = "Id is required") @PathVariable("id") final String id) {
        productService.deleteById(id);
    }

    /**
     * Update a product.
     *
     * @param id to be used to search the product
     * @param updateFields {@link Map} map containing fields to be updated
     * @return {@link ProductResponseDto} product response data transfer object
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(method = PATCH_METHOD, summary = "Update product field by id")
    @Parameter(in = ParameterIn.PATH, name = "id", required = true, example = "26809cf3-3687-42f5-9ed0-1d7e0e2e81ef")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Product request data transfer object",
            required = true,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductRequestDto.class))
    )
    @ApiResponse(
            responseCode = HttpStatusDescriptionConstants.SUCCESS_REQUEST_CODE,
            description = HttpStatusDescriptionConstants.SUCCESS_REQUEST_DESCRIPTION,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductResponseDto.class))
    )
    @ApiResponse(
            responseCode = HttpStatusDescriptionConstants.BAD_REQUEST_CODE,
            description = HttpStatusDescriptionConstants.BAD_REQUEST_DESCRIPTION,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @ApiResponse(
            responseCode = HttpStatusDescriptionConstants.NOT_FOUND_REQUEST_CODE,
            description = HttpStatusDescriptionConstants.NOT_FOUND_REQUEST_DESCRIPTION,
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RestErrorResponseDto.class))
    )
    @PatchMapping(value = "/product/{id}")
    public ProductResponseDto updateProduct(@NotBlank(message = "Id is required") @PathVariable("id") final String id,
                                            @RequestBody Map<String, Object> updateFields) {
        var product = productService.updateById(id, updateFields);
        return productMapper.productToProductResponseDto(product);
    }

}
