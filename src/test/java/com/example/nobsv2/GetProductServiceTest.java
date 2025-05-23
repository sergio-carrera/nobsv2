package com.example.nobsv2;


import com.example.nobsv2.product.ProductRepository;
import com.example.nobsv2.product.exceptions.ProductNotFoundException;
import com.example.nobsv2.product.model.Product;
import com.example.nobsv2.product.model.ProductDTO;
import com.example.nobsv2.product.services.GetProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetProductServiceTest {

    @Mock //what mock the response of something -> need this dependency to run the test
    private ProductRepository productRepository;

    @InjectMocks //the thing we are testing
    private GetProductService getProductService;

    @BeforeEach //things we need beefore the test runs to set up properly
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void give_product_exists_when_get_products_service_return_product_dto() {
        //Given
        Product product = new Product();
        product.setId(1);
        product.setName("Dark souls 2");
        product.setDescription("Product Description which is at least 20 characters");
        product.setPrice(20.0);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        //When
        ResponseEntity<ProductDTO> response = getProductService.execute(1);

        //Then
        assertEquals(ResponseEntity.ok(new ProductDTO(product)), response);
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    public void give_product_does_not_exist_when_get_products_service_return_product_dto() {
        //Given
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        //When & Then
        assertThrows(ProductNotFoundException.class, () -> getProductService.execute(1));
        verify(productRepository, times(1)).findById(1);
    }
}
