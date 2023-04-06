package ru.egorov.onlinestoreapi.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.egorov.onlinestoreapi.dto.ErrorDto;
import ru.egorov.onlinestoreapi.exception.BadCredentialsException;
import ru.egorov.onlinestoreapi.exception.ProductNotAddedOrUpdatedException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleException(BadCredentialsException e) {

        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleException(ProductNotAddedOrUpdatedException e) {

        return new ResponseEntity<>(new ErrorDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
