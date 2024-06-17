package com.podoarena.exception;

public class OutOfStockException extends RuntimeException{
    //재고 부족 exception
    public OutOfStockException(String message) {
        super(message);
    }
}
