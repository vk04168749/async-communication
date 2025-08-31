package com.cnx.ecom.payment.exception;

import lombok.Getter;

@Getter
public class PaymentException extends RuntimeException {

    private final int statusCode;

    public PaymentException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode(){
        return statusCode;
    }
}
