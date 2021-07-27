package com.currency.converter.controllers.handlers;

import com.currency.converter.dto.ErrorDTO;
import com.currency.converter.dto.FieldErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

import static java.util.Comparator.comparing;

@Slf4j
@RestControllerAdvice
public class ValidationErrorHandler {


    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDTO handle (MethodArgumentNotValidException exception){
        var error = new ErrorDTO();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setType("http://currency-converter-spring/validation-error");
        error.setTitle("Invalid Fields");
        error.setDetail("The fields passed in the request are not valid");

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            error.getFields().add(new FieldErrorDTO(e.getField(),e.getDefaultMessage()));
        });

        error.getFields().sort(comparing(FieldErrorDTO::getName));

        log.error("VALIDATION ERROR ",exception);

        return error;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorDTO handle (HttpMessageNotReadableException exception){
        var error = new ErrorDTO();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setType("http://currency-converter-spring/validation-error");
        error.setTitle("Deserialize Error");
        error.setDetail(exception.getMessage());

        log.error("DESERIALIZE ERROR",exception);

        return error;
    }

    @ResponseStatus(code = HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(ResourceAccessException.class)
    public ErrorDTO handle (ResourceAccessException exception){
        var error = new ErrorDTO();
        error.setStatus(HttpStatus.BAD_GATEWAY.value());
        error.setType("http://currency-converter-spring/bad-gateway");
        error.setTitle("Bad Gateway");
        error.setDetail("Unable to connect to exchange server, please try again later");

        log.error("UNABLE TO CONNECT TO EXCHANGE SERVER",exception);

        return error;
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDTO handle (Exception exception){
        var error = new ErrorDTO();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setType("http://currency-converter-spring/internal-error");
        error.setTitle("Internal Error");
        error.setDetail("Internal Error");

        log.error("INTERNAL ERROR",exception);

        return error;
    }
}
