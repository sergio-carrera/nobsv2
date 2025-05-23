package com.example.nobsv2.product.services;

import com.example.nobsv2.Query;
import com.example.nobsv2.product.ProductRepository;
import com.example.nobsv2.product.exceptions.ProductNotFoundException;
import com.example.nobsv2.product.model.Product;
import com.example.nobsv2.product.model.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetProductService implements Query<Integer, ProductDTO> {

    private ProductRepository productRepository;

    public GetProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(Integer input) {
        //Account for null value id db can't find it
        Optional<Product> productOptional = productRepository.findById(input);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(new ProductDTO(productOptional.get()));
        }

        //In the future we will want to throw a Product not found exception
        throw new ProductNotFoundException();
    }
}
