package com.philips.productcatalog.core.handler;

import com.philips.productcatalog.api.dto.error.RestErrorResponseDto;
import com.philips.productcatalog.domain.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Global exception handler.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String STANDARD_LOG_ERROR = "HTTP UserInfoStatus: {} - Error Message: {}";

    /**
     * Handles method argument not valid exception.
     *
     * @param exception method argument not valid exception
     * @param headers   http headers
     * @param status    http status
     * @param request   web request
     * @return response entity containing error response
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        var invalidRequest = "Invalid Request";
        final List<String> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errors.add(error.getField() + ": " + error.getDefaultMessage()));
        log.error(STANDARD_LOG_ERROR, HttpStatus.BAD_REQUEST, invalidRequest + " " + errors);
        var restErrorResponseDto = new RestErrorResponseDto(HttpStatus.BAD_REQUEST, invalidRequest, errors);
        return handleExceptionInternal(exception, restErrorResponseDto, headers, restErrorResponseDto.getStatus(), request);
    }

    /**
     * Handles http media type not supported exception.
     *
     * @param exception http media type not supported exception
     * @param headers   http headers
     * @param status    http status
     * @param request   web request
     * @return response entity containing error response
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {

        var message = "Supported content types: ".concat(MediaType.toString(exception.getSupportedMediaTypes()));
        var restErrorResponseDto = new RestErrorResponseDto(HttpStatus.BAD_REQUEST, message);
        log.error(STANDARD_LOG_ERROR, HttpStatus.UNSUPPORTED_MEDIA_TYPE, message);
        return handleExceptionInternal(exception, restErrorResponseDto, headers, restErrorResponseDto.getStatus(), request);
    }

    /**
     * Handles http request method not supported exception.
     *
     * @param exception http request method not supported exception
     * @param headers   http headers
     * @param status    http status
     * @param request   web request
     * @return response entity containing error response
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request) {
        final StringBuilder builder = new StringBuilder()
                .append(exception.getMethod())
                .append(" method is not supported for this request.")
                .append(" Supported methods are ");
        Optional.of(exception).map(HttpRequestMethodNotSupportedException::getSupportedHttpMethods)
                .ifPresent(allowedMethod -> builder.append(allowedMethod).append(" "));
        var errorMessage = builder.toString();
        log.error(STANDARD_LOG_ERROR, HttpStatus.METHOD_NOT_ALLOWED, errorMessage);
        var restErrorResponseDto = new RestErrorResponseDto(HttpStatus.METHOD_NOT_ALLOWED, errorMessage);
        return new ResponseEntity<>(restErrorResponseDto, restErrorResponseDto.getStatus());
    }

    /**
     * Handles not found exception.
     *
     * @param exception not found exception
     * @return response entity containing error response
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorResponseDto> handleNotFound(Exception exception) {
        return buildRestErrorResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    private ResponseEntity<RestErrorResponseDto> buildRestErrorResponseEntity(HttpStatus httpStatus, String errorMessage) {
        log.error(STANDARD_LOG_ERROR, httpStatus, errorMessage);
        var restErrorResponseDto = new RestErrorResponseDto(httpStatus, errorMessage);
        return new ResponseEntity<>(restErrorResponseDto, restErrorResponseDto.getStatus());
    }
}
