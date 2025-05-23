package com.example.nobsv2;

import com.example.nobsv2.product.ProductRepository;
import com.example.nobsv2.product.exceptions.ErrorMessages;
import com.example.nobsv2.product.exceptions.ProductNotValidException;
import com.example.nobsv2.product.model.Product;
import com.example.nobsv2.product.model.ProductDTO;
import com.example.nobsv2.product.services.CreateProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateProductServiceTest {
    @Mock //what mock the response of something -> need this dependency to run the test
    private ProductRepository productRepository;

    @InjectMocks //the thing we are testing
    private CreateProductService createProductService;

    @BeforeEach //things we need beefore the test runs to set up properly
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /*
    Se me ocurren 6 caminos lógicos:
    1. Que el Product salvado en la base de datos me devuelva el ProductDTO
    2. Que el flujo del servicio arroje una excepción porque el 'name' no cumple con las validaciones (es "")
    3. Que el flujo del servicio arroje una excepción porque el 'name' no cumple con las validaciones (es null)
    4. Que el flujo del servicio arroje una excepción porque la 'description' no cumple con las validaciones
    5. Que el flujo del servicio arroje una excepción porque el 'price' no cumple con las validaciones (es negativo)
    6. Que el flujo del servicio arroje una excepción porque el 'price' no cumple con las validaciones (es null)
    */

    @Test
    @DisplayName("Should return ProductDTO when valid product is provided") // Esto mejora la claridad de la documentación
    public void give_product_entered_when_create_product_service_return_product_dto() {
        //Given
        Product product = new Product();
        product.setId(1);
        product.setName("Ejemplo");
        product.setDescription("Description must be at least of 20 characters");
        product.setPrice(4.1);

        //when(productRepository.save(product)).thenReturn(product); -> lo cambio por:
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        //When
        ResponseEntity<ProductDTO> response = createProductService.execute(product);

        //Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(product.getId(), response.getBody().getId());
        assertEquals(product.getName(), response.getBody().getName());
        assertEquals(product.getDescription(), response.getBody().getDescription());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void give_product_entered_with_empty_name_when_create_product_service_return_product_dto() {
        //Given
        Product product = new Product();
        product.setId(1);
        product.setName(""); // <- nombre vacío
        product.setDescription("Description must be at least of 20 characters");
        product.setPrice(4.1);

        //When & Then
        ProductNotValidException exception = assertThrows(
                ProductNotValidException.class,
                () -> createProductService.execute(product)
        );

        assertEquals(ErrorMessages.NAME_REQUIRED.getMessage(), exception.getMessage()); // o el mensaje exacto en ErrorMessages
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void give_product_entered_with_null_name_when_create_product_service_return_product_dto() {
        //Given
        Product product = new Product();
        product.setId(1);
        product.setName(null); // <- nombre nulo
        product.setDescription("Description must be at least of 20 characters");
        product.setPrice(4.1);

        //When & Then
        ProductNotValidException exception = assertThrows(
                ProductNotValidException.class,
                () -> createProductService.execute(product)
        );

        assertEquals(ErrorMessages.NAME_REQUIRED.getMessage(), exception.getMessage()); // o el mensaje exacto en ErrorMessages

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void give_product_entered_with_short_description_when_create_product_service_return_product_dto() {
        //Given
        Product product = new Product();
        product.setId(1);
        product.setName("Ejemplo");
        product.setDescription("Description"); // <- descripción menor a 20 caracteres
        product.setPrice(4.1);

        //When & Then
        ProductNotValidException exception = assertThrows(
                ProductNotValidException.class,
                () -> createProductService.execute(product)
        );
        assertEquals(ErrorMessages.DESCRIPTION_LENGTH.getMessage(), exception.getMessage()); // o el mensaje exacto en ErrorMessages
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void give_product_entered_with_negative_price_when_create_product_service_return_product_dto() {
        //Given
        Product product = new Product();
        product.setId(1);
        product.setName("Ejemplo");
        product.setDescription("Description must be at least of 20 characters");
        product.setPrice(-4.1); // <- precio negativo

        //When & Then
        ProductNotValidException exception = assertThrows(
                ProductNotValidException.class,
                () -> createProductService.execute(product)
        );

        assertEquals(ErrorMessages.PRICE_CANNOT_BE_NEGATIVE.getMessage(), exception.getMessage()); // o el mensaje exacto en ErrorMessages
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void give_product_entered_with_null_price_when_create_product_service_return_product_dto() {
        //Given
        Product product = new Product();
        product.setId(1);
        product.setName("Ejemplo");
        product.setDescription("Description must be at least of 20 characters");
        product.setPrice(null); // <- precio null

        //When & Then
        ProductNotValidException exception = assertThrows(
                ProductNotValidException.class,
                () -> createProductService.execute(product)
        );

        assertEquals(ErrorMessages.PRICE_CANNOT_BE_NEGATIVE.getMessage(), exception.getMessage()); // o el mensaje exacto en ErrorMessages
        verify(productRepository, never()).save(any(Product.class));
    }
}
