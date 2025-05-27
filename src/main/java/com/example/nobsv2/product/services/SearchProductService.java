package com.example.nobsv2.product.services;

import com.example.nobsv2.Query;
import com.example.nobsv2.product.ProductController;
import com.example.nobsv2.product.ProductRepository;
import com.example.nobsv2.product.model.Product;
import com.example.nobsv2.product.model.ProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchProductService implements Query<String, List<ProductDTO>> {

    private final ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(GetProductService.class);

    public SearchProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<List<ProductDTO>> execute(String name) {
        logger.info("Executing " + getClass() + " input/name: " + name);
        return ResponseEntity.ok(productRepository.findByNameOrDescriptionContaining(name)
                .stream()
                .map(ProductDTO::new)
                .toList());
    }
}

/*
Estas 3 opciones hacen lo mismo y son igual de válidas

//Opción 1
public ResponseEntity<List<ProductDTO>> execute(String name) {
    return ResponseEntity.ok(productRepository.findByNameContaining(name)
            .stream()
            .map(ProductDTO::new)
            .toList());
}

//Opción 2
public ResponseEntity<List<ProductDTO>> execute(String name) {
    List<Product> products = productRepository.findByNameContaining(name);
    List<ProductDTO> productDTOS = products.stream().map(ProductDTO::new).toList();

    return ResponseEntity.status(HttpStatus.OK).body(productDTOS);
}

//Opción 3
public ResponseEntity<List<ProductDTO>> execute(String name) {
    return ResponseEntity.status(HttpStatus.OK).body(productRepository.findByNameContaining(name)
            .stream()
            .map(ProductDTO::new)
            .toList());
}
*/
