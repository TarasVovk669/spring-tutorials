package com.reactive.webflux.demo.sec02.exceptions;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Integer message) {
        super("default");
    }

}
