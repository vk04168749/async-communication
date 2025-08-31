package com.cnx.ecom.customer.exception;

import lombok.Getter;

@Getter
public class CustomerException extends RuntimeException {

    private final int statusCode;

    public CustomerException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

}
