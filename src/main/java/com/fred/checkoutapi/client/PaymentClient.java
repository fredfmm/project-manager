package com.fred.checkoutapi.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    @ExceptionHandler(value = {Exception.class})
    ResponseEntity<Object> handleGeneralException(Exception e) {
        log.error("Uncaught exception, message={}", e.getMessage(), e);
        return status(INTERNAL_SERVER_ERROR).body(new ErrorMessage(e.getMessage()));
    }


    @ExceptionHandler(value = {IllegalArgumentException.class, MethodArgumentTypeMismatchException.class})
    ResponseEntity<Object> handleInvalidRequest(Exception e) {
        return status(BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(value = {InvalidFormatException.class, HttpMessageNotReadableException.class})
    ResponseEntity<Object> handleInvalidFormatException(Exception e) {
        log.warn(e.getMessage());
        return status(BAD_REQUEST).body(new ErrorMessage("message.malformed-request"));
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return status(METHOD_NOT_ALLOWED).body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<Object> handleIllegalStateException(IllegalStateException e) {
        log.warn(e.getMessage());
        return status(FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    ResponseEntity<Object> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.warn(e.getMessage());
        return status(UNSUPPORTED_MEDIA_TYPE).build();
    }


    @ExceptionHandler({NotFoundException.class})
    ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        log.warn(e.getMessage());
        return status(NOT_FOUND).body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(ResourceAccessException.class)
    ResponseEntity<Object> handleResourceAccessException(ResourceAccessException e) {
        log.error(e.getMessage(), e);
        return status(BAD_GATEWAY).body(new ErrorMessage("message.integration.connection.refused"));
    }


    @Data
    @AllArgsConstructor
    public static class ErrorMessage {
        private List<String> messages;

        public ErrorMessage(String message) {
            messages = List.of(message);
        }
    }
}
