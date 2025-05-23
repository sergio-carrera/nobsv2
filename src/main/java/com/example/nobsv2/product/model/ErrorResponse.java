package com.example.nobsv2.product.model;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
