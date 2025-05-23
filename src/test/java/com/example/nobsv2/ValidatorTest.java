package com.example.nobsv2;

import com.example.nobsv2.product.exceptions.ErrorMessages;
import com.example.nobsv2.product.exceptions.ProductNotValidException;
import com.example.nobsv2.product.model.Product;
import com.example.nobsv2.validators.ProductValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {
    @Test
    @DisplayName("Should pass validation for valid product")
    public void givenValidProduct_whenValidate_thenNoExceptionThrown() {
        Product product = new Product();
        product.setName("Valid Name");
        product.setDescription("This is a valid product description.");
        product.setPrice(10.0);

        assertDoesNotThrow(() -> ProductValidator.execute(product));
    }

    @Test
    @DisplayName("Should throw exception when name is empty")
    public void givenProductWithEmptyName_whenValidate_thenThrowProductNotValidException() {
        Product product = new Product();
        product.setName(""); // nombre vacío
        product.setDescription("This is a valid product description.");
        product.setPrice(10.0);

        ProductNotValidException exception = assertThrows(
                ProductNotValidException.class,
                () -> ProductValidator.execute(product)
        );

        assertEquals(ErrorMessages.NAME_REQUIRED.getMessage(), exception.getMessage());
    }


    @Test
    @DisplayName("Should throw exception when name is null")
    public void givenProductWithNullName_whenValidate_thenThrowProductNotValidException() {
        Product product = new Product();
        product.setName(null); // nombre nulo
        product.setDescription("This is a valid product description.");
        product.setPrice(10.0);

        ProductNotValidException exception = assertThrows(
                ProductNotValidException.class,
                () -> ProductValidator.execute(product)
        );

        assertEquals(ErrorMessages.NAME_REQUIRED.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when description is too short")
    public void givenProductWithShortDescription_whenValidate_thenThrowProductNotValidException() {
        Product product = new Product();
        product.setName("Valid Name");
        product.setDescription("Too short"); // menos de 20
        product.setPrice(10.0);

        ProductNotValidException exception = assertThrows(
                ProductNotValidException.class,
                () -> ProductValidator.execute(product)
        );

        assertEquals(ErrorMessages.DESCRIPTION_LENGTH.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when price is negative")
    public void givenProductWithNegativePrice_whenValidate_thenThrowProductNotValidException() {
        Product product = new Product();
        product.setName("Valid Name");
        product.setDescription("This is a valid product description.");
        product.setPrice(-5.0);

        ProductNotValidException exception = assertThrows(
                ProductNotValidException.class,
                () -> ProductValidator.execute(product)
        );

        assertEquals(ErrorMessages.PRICE_CANNOT_BE_NEGATIVE.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when price is null")
    public void givenProductWithNullPrice_whenValidate_thenThrowProductNotValidException() {
        Product product = new Product();
        product.setName("Valid Name");
        product.setDescription("This is a valid product description.");
        product.setPrice(null);

        ProductNotValidException exception = assertThrows(
                ProductNotValidException.class,
                () -> ProductValidator.execute(product)
        );

        assertEquals(ErrorMessages.PRICE_CANNOT_BE_NEGATIVE.getMessage(), exception.getMessage());
    }
}
