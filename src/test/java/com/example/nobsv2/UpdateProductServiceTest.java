package com.example.nobsv2;

import com.example.nobsv2.product.ProductRepository;
import com.example.nobsv2.product.exceptions.ErrorMessages;
import com.example.nobsv2.product.exceptions.ProductNotFoundException;
import com.example.nobsv2.product.exceptions.ProductNotValidException;
import com.example.nobsv2.product.model.Product;
import com.example.nobsv2.product.model.UpdateProductCommand;
import com.example.nobsv2.product.model.ProductDTO;
import com.example.nobsv2.product.services.UpdateProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateProductServiceTest {
    @Mock //what mock the response of something -> need this dependency to run the test
    private ProductRepository productRepository;

    @InjectMocks //the thing we are testing
    private UpdateProductService updateProductService;

    @BeforeEach //things we need beefore the test runs to set up properly
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /*
    Se me ocurren 7 caminos lógicos:
    1. Que exista el Product a actualizar (con base al @PathVariable id), que pase las validaciones, que se guarde y que se devuelva el ProductDTO
    2. Que no exista el Product a actualizar (con base al @PathVariable id) y que arroje la excepción manejada ProductNotFoundException()
    3. Que exista el Product a actualizar (con base al @PathVariable id), pero que no pase las validaciones debido al 'name' ("") y que arroje la
    excepción con el mensaje acorde de ProductNotValidException
    4. Que exista el Product a actualizar (con base al @PathVariable id), pero que no pase las validaciones debido al 'name' (null) y que arroje la
    excepción con el mensaje acorde de ProductNotValidException
    5. Que exista el Product a actualizar (con base al @PathVariable id), pero que no pase las validaciones debido al 'description' y que arroje la
    excepción con el mensaje acorde de ProductNotValidException
    6. Que exista el Product a actualizar (con base al @PathVariable id), pero que no pase las validaciones debido al 'price' (negativo) y que arroje la
    excepción con el mensaje acorde de ProductNotValidException
    7. Que exista el Product a actualizar (con base al @PathVariable id), pero que no pase las validaciones debido al 'price' (null) y que arroje la
    excepción con el mensaje acorde de ProductNotValidException
    */

    @Test
    @DisplayName("Given existing product with valid data, when updating, then return updated ProductDTO")
    public void givenExistingProductWithValidData_whenUpdateProduct_thenReturnUpdatedProductDTO() {
        //Given
        int productId = 1;

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Old Name");
        existingProduct.setDescription("Old description more than 20 chars");
        existingProduct.setPrice(10.0);

        Product updatedProduct = new Product();
        updatedProduct.setName("Dark souls 2");
        updatedProduct.setDescription("Product Description which is at least 20 characters");
        updatedProduct.setPrice(20.0);

        UpdateProductCommand command = new UpdateProductCommand(productId, updatedProduct);
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //When
        ResponseEntity<ProductDTO> response = updateProductService.execute(command);

        //Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(productId, response.getBody().getId());
        assertEquals(updatedProduct.getName(), response.getBody().getName());
        assertEquals(updatedProduct.getDescription(), response.getBody().getDescription());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Given non-existing product ID, when updating, then throw ProductNotFoundException")
    public void givenNonExistingProductId_whenUpdateProduct_thenThrowProductNotFoundException() {
        // Given
        int nonExistentId = 99;
        Product product = new Product();
        product.setName("Dark Souls 2");
        product.setDescription("A product description longer than 20 chars");
        product.setPrice(20.0);

        UpdateProductCommand command = new UpdateProductCommand(nonExistentId, product);

        when(productRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        //When & Then
        assertThrows(ProductNotFoundException.class, () -> updateProductService.execute(command));

        verify(productRepository, times(1)).findById(nonExistentId);
        verify(productRepository, never()).save(any(Product.class));
    }


    @Test
    @DisplayName("Given existing product with empty name, when updating, then throw ProductNotValidException with name message")
    public void givenExistingProductWithEmptyName_whenUpdateProduct_thenThrowProductNotValidException() {
        //Given
        int existingId = 1;
        Product product = new Product();
        product.setName(""); // <- nombre vacío
        product.setDescription("Valid description with more than 20 characters");
        product.setPrice(10.0);

        UpdateProductCommand command = new UpdateProductCommand(existingId, product);

        when(productRepository.findById(existingId)).thenReturn(Optional.of(new Product()));

        //When & Then
        ProductNotValidException exception = assertThrows(
                ProductNotValidException.class,
                () -> updateProductService.execute(command)
        );

        assertEquals(ErrorMessages.NAME_REQUIRED.getMessage(), exception.getMessage());

        verify(productRepository, times(1)).findById(existingId);
        verify(productRepository, never()).save(any(Product.class));
    }


    @Test
    @DisplayName("Given existing product with null name, when updating, then throw ProductNotValidException with name message")
    public void givenExistingProductWithNullName_whenUpdateProduct_thenThrowProductNotValidException() {
        //Given
        int existingId = 1;
        Product product = new Product();
        product.setName(null); // <- nombre vacío
        product.setDescription("Valid description with more than 20 characters");
        product.setPrice(10.0);

        UpdateProductCommand command = new UpdateProductCommand(existingId, product);

        when(productRepository.findById(existingId)).thenReturn(Optional.of(new Product()));

        //When & Then
        ProductNotValidException exception = assertThrows(
                ProductNotValidException.class,
                () -> updateProductService.execute(command)
        );

        assertEquals(ErrorMessages.NAME_REQUIRED.getMessage(), exception.getMessage());

        verify(productRepository, times(1)).findById(existingId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Given existing product with short description, when updating, then throw ProductNotValidException with description message")
    public void givenExistingProductWithShortDescription_whenUpdateProduct_thenThrowProductNotValidException() {
        //Given
        int existingId = 1;
        Product product = new Product();
        product.setName("Ejemplo");
        product.setDescription("Invalid desciption"); // <- descripción corta
        product.setPrice(10.0);

        UpdateProductCommand command = new UpdateProductCommand(existingId, product);

        when(productRepository.findById(existingId)).thenReturn(Optional.of(new Product()));

        //When & Then
        ProductNotValidException exception = assertThrows(
                ProductNotValidException.class,
                () -> updateProductService.execute(command)
        );

        assertEquals(ErrorMessages.DESCRIPTION_LENGTH.getMessage(), exception.getMessage());

        verify(productRepository, times(1)).findById(existingId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Given existing product with negative price, when updating, then throw ProductNotValidException with price message")
    public void givenExistingProductWithNegativePrice_whenUpdateProduct_thenThrowProductNotValidException() {
        //Given
        int existingId = 1;
        Product product = new Product();
        product.setName("Ejemplo");
        product.setDescription("Valid description with more than 20 characters");
        product.setPrice(-10.0); // <- precio negativo

        UpdateProductCommand command = new UpdateProductCommand(existingId, product);

        when(productRepository.findById(existingId)).thenReturn(Optional.of(new Product()));

        //When & Then
        ProductNotValidException exception = assertThrows(
                ProductNotValidException.class,
                () -> updateProductService.execute(command)
        );

        assertEquals(ErrorMessages.PRICE_CANNOT_BE_NEGATIVE.getMessage(), exception.getMessage());

        verify(productRepository, times(1)).findById(existingId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Given existing product with null price, when updating, then throw ProductNotValidException with price message")
    public void givenExistingProductWithNullPrice_whenUpdateProduct_thenThrowProductNotValidException() {
        //Given
        int existingId = 1;
        Product product = new Product();
        product.setName("Ejemplo");
        product.setDescription("Valid description with more than 20 characters");
        product.setPrice(null); // <- precio 'null'

        UpdateProductCommand command = new UpdateProductCommand(existingId, product);

        when(productRepository.findById(existingId)).thenReturn(Optional.of(new Product()));

        //When & Then
        ProductNotValidException exception = assertThrows(
                ProductNotValidException.class,
                () -> updateProductService.execute(command)
        );

        assertEquals(ErrorMessages.PRICE_CANNOT_BE_NEGATIVE.getMessage(), exception.getMessage());

        verify(productRepository, times(1)).findById(existingId);
        verify(productRepository, never()).save(any(Product.class));
    }
}
