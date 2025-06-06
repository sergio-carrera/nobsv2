package com.example.nobsv2;

import com.example.nobsv2.product.ProductRepository;
import com.example.nobsv2.product.exceptions.ProductNotFoundException;
import com.example.nobsv2.product.model.Product;
import com.example.nobsv2.product.model.ProductDTO;
import com.example.nobsv2.product.services.DeleteProductService;
import com.example.nobsv2.product.services.GetProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeleteProductServiceTest {
    @Mock //what mock the response of something -> need this dependency to run the test
    private ProductRepository productRepository;

    @InjectMocks //the thing we are testing
    private DeleteProductService deleteProductService;

    @BeforeEach //things we need beefore the test runs to set up properly
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /*
    Se me ocurren dos caminos lógicos:
    1. Elimino el producto y retorno status code NO_CONTENT y no regreso body
    2. No elimino el producto y manejo la excepción de ProductNotFoundException()
    */
    @Test
    public void give_product_exists_when_delete_product_service_return_void() {
        //Given
        Product product = new Product();
        product.setId(1);
        product.setName("Dark souls 2");
        product.setDescription("Product Description which is at least 20 characters");
        product.setPrice(20.0);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        //When
        ResponseEntity<Void> response = deleteProductService.execute(1);

        //Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(productRepository, times(1)).findById(1);
        verify(productRepository, times(1)).deleteById(1);
    }


    @Test
    public void give_product_does_not_exist_when_delete_product_service_return_void() {
        //Given
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        //When & Then
        assertThrows(ProductNotFoundException.class, () -> deleteProductService.execute(1));
        verify(productRepository, times(1)).findById(1);
    }

}
