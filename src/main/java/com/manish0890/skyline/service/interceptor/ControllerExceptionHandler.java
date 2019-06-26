package com.manish0890.skyline.service.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.manish0890.skyline.service.document.dto.ErrorDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.util.Date;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<Object> processValidationError(HttpMessageConversionException ex) {
        LOGGER.trace("Custom Handling HttpMessageConversionException");

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(LocalDate.now().toString());

        if(ex.getCause() instanceof InvalidDefinitionException
                && ex.getCause().getCause() instanceof IllegalArgumentException) {
            errorDetail.setStatus(HttpStatus.PRECONDITION_FAILED.value());
            errorDetail.setMessage(ex.getCause().getCause().getMessage());
            errorDetail.setError(HttpStatus.PRECONDITION_FAILED.getReasonPhrase());
        } else {
            errorDetail.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            errorDetail.setMessage(ex.getCause().getMessage());
            errorDetail.setError(ex.getCause().getMessage());
        }

        LOGGER.error(errorDetail.getError(), ex);

        return new ResponseEntity<>(getStringErrorDetail(errorDetail), HttpStatus.valueOf(errorDetail.getStatus()));
    }

    // Size Expectation Error Handler and more
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.trace("Custom Handling MethodArgumentNotValidException exception");

        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().toString());
        errorDetail.setError(status.getReasonPhrase());
        errorDetail.setStatus(status.value());
        errorDetail.setMessage(ex.getBindingResult().getFieldError().getDefaultMessage());

        LOGGER.error(errorDetail.getError(), ex);

        return new ResponseEntity<>(getStringErrorDetail(errorDetail), HttpStatus.valueOf(errorDetail.getStatus()));
    }

    private String getStringErrorDetail(ErrorDetail errorDetail) {
        try {
            return new ObjectMapper().writeValueAsString(errorDetail);
        } catch (JsonProcessingException e) {
            return errorDetail.toString();
        }
    }
}
