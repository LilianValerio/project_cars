package com.project.cars.errors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.project.cars.dto.ErrorsDTO;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmailAlredyExistsException.class)
    protected ResponseEntity<Object> emailAlreadyExists(
            final EmailAlredyExistsException ex) {
        return buildResponseEntity(new ErrorsDTO(ex.getMessage(), HttpStatus.CONFLICT.value()));
    }
    
    @ExceptionHandler(LoginAlredyExistsException.class)
    protected ResponseEntity<Object> emailAlreadyExists(
            final LoginAlredyExistsException ex) {
        return buildResponseEntity(new ErrorsDTO(ex.getMessage(), HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(InvalidLoginOrPasswordException.class)
    protected ResponseEntity<Object> InvalidLoginOrPassword(
            final InvalidLoginOrPasswordException ex) {
        return buildResponseEntity(new ErrorsDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(InvalidFieldsException.class)
    protected ResponseEntity<Object> invalidFields(
            final InvalidFieldsException ex) {
        return buildResponseEntity(new ErrorsDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(MissingFieldsException.class)
    protected ResponseEntity<Object> missingFields(
            final MissingFieldsException ex) {
        return buildResponseEntity(new ErrorsDTO(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    protected ResponseEntity<Object> unauthorizedToken(
            final InvalidTokenException ex) {
        return buildResponseEntity(new ErrorsDTO(ex.getMessage(), HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler(ExpiredTokenException.class)
    protected ResponseEntity<Object> expiredToken(
            final ExpiredTokenException ex) {
        return buildResponseEntity(new ErrorsDTO(ex.getMessage(), HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> unauthorizedToken(
            final IllegalArgumentException ex) {
        return buildResponseEntity(new ErrorsDTO("Unauthorized", HttpStatus.UNAUTHORIZED.value()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex,
                                                                  final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request) {
        final String error = "Malformed JSON request";
        return buildResponseEntity(new ErrorsDTO(error, status.value()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> defaultHandler(
            final Exception ex) {

        return buildResponseEntity(new ErrorsDTO("Something went wrong.", HttpStatus.BAD_REQUEST.value()));
    }

    private ResponseEntity<Object> buildResponseEntity(final ErrorsDTO apiError) {
        return new ResponseEntity<>(apiError, HttpStatus.resolve(apiError.getErrorCode()));
    }
}