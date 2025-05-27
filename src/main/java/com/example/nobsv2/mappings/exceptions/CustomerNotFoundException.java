package com.example.nobsv2.mappings.exceptions;

import com.example.nobsv2.product.exceptions.ErrorMessages;
import com.example.nobsv2.product.exceptions.ProductNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException{

    private static final Logger logger = LoggerFactory.getLogger(ProductNotFoundException.class);

    public CustomerNotFoundException() {
        super(ErrorMessages.CUSTOMER_NOT_FOUND.getMessage());
        logger.error("Exception " + getClass() + " thrown");
    }
}
