package com.example.nobsv2.product.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductNotValidException extends RuntimeException{

    private static final Logger logger = LoggerFactory.getLogger(ProductNotFoundException.class);

    public ProductNotValidException(String message) {
        super(message);
        logger.error("Exception " + getClass() + " thrown");
    }
}
