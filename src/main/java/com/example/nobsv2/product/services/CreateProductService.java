package com.example.nobsv2.product.services;

import com.example.nobsv2.Command;
import com.example.nobsv2.product.ProductRepository;
import com.example.nobsv2.product.exceptions.ErrorMessages;
import com.example.nobsv2.product.exceptions.ProductNotValidException;
import com.example.nobsv2.product.model.Product;
import com.example.nobsv2.product.model.ProductDTO;
import com.example.nobsv2.validators.ProductValidator;
import com.mysql.cj.util.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateProductService implements Command<Product, ProductDTO> {

    private final ProductRepository productRepository;

    public CreateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<ProductDTO> execute(Product product) {
        //validate before saving
        ProductValidator.execute(product);
        Product productSaved = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductDTO(productSaved));
    }
}
