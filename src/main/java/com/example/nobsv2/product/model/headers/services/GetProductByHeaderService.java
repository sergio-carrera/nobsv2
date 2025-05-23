package com.example.nobsv2.product.model.headers.services;

import com.example.nobsv2.Query;
import com.example.nobsv2.product.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetProductByHeaderService implements Query<Void, Product> {
    @Override
    public ResponseEntity<Product> execute(Void input) {
        Product product = new Product();
        product.setId(1);
        product.setName("Producto por header");
        product.setDescription("Description");
        product.setPrice(20.2);

        return ResponseEntity.ok(product);
    }
}
