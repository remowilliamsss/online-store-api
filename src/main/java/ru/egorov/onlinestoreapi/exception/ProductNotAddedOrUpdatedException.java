package ru.egorov.onlinestoreapi.exception;

public class ProductNotAddedOrUpdatedException extends RuntimeException {

    public ProductNotAddedOrUpdatedException(String message) {
        super(message);
    }
}
