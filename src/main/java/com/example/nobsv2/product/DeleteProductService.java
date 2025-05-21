package com.example.nobsv2.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DeleteProductService implements Command<Void, String>{
    @Override
    public ResponseEntity<String> execute(Void input) {
        return null;
    }
}
