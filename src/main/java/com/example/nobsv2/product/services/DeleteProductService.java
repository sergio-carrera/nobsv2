package com.example.nobsv2.product.services;

import com.example.nobsv2.Command;
import com.example.nobsv2.product.ProductRepository;
import com.example.nobsv2.product.exceptions.ProductNotFoundException;
import com.example.nobsv2.product.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class DeleteProductService implements Command<Integer, Void> {

    private final ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(GetProductService.class);

    public DeleteProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ResponseEntity<Void> execute(Integer id) {
        logger.info("Executing " + getClass() + " id: " + id);

        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new ProductNotFoundException();
    }
}
