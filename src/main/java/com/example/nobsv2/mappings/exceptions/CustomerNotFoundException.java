package com.example.nobsv2.mappings.exceptions;

import com.example.nobsv2.product.exceptions.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException() {
        super(ErrorMessages.CUSTOMER_NOT_FOUND.getMessage());
    }
}
