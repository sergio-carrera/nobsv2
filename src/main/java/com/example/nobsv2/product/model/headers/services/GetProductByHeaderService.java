package com.example.nobsv2.product.model.headers.services;

import com.example.nobsv2.Query;
import com.example.nobsv2.product.model.Product;
import com.example.nobsv2.product.services.GetProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetProductByHeaderService implements Query<Void, Product> {

    private static final Logger logger = LoggerFactory.getLogger(GetProductService.class);

    @Override
    public ResponseEntity<Product> execute(Void input) {

        logger.info("Executing " + getClass() + " input is a void");

        Product product = new Product();
        product.setId(1);
        product.setName("Producto por header");
        product.setDescription("Description");
        product.setPrice(20.2);

        return ResponseEntity.ok(product);
    }
}
