package com.example.nobsv2;

import com.example.nobsv2.product.ProductRepository;
import com.example.nobsv2.product.model.Product;
import com.example.nobsv2.product.model.ProductDTO;
import com.example.nobsv2.product.services.GetProductsService;
import com.example.nobsv2.product.services.SearchProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SearchProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private SearchProductService searchProductService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /*
    Se me ocurren dos caminos lógicos:
    1. Devuelve una lista con valores
    3. Devuelve lista vacía

    Nota: Debe devolver status code de 200 (ok) al ser metodo Get
    */

    //Devuelve lista con valores
    @Test
    public void give_products_exists_based_on_name_or_description_get_search_service_return_list_of_product_dto() {
        //Given
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Dark souls 2");
        product1.setDescription("Product Description which is at least 20 characters");
        product1.setPrice(20.0);

        Product product2 = new Product();
        product2.setId(1);
        product2.setName("Demon Souls");
        product2.setDescription("Product Description which is at least 20 characters");
        product2.setPrice(99.9);

        List<Product> products = List.of(product1, product2);

        when(productRepository.findByNameOrDescriptionContaining("souls")).thenReturn(products);

        //When
        ResponseEntity<List<ProductDTO>> response = searchProductService.execute("souls");

        //Then
        List<ProductDTO> expectedDtos = products.stream().map(ProductDTO::new).toList();
        assertEquals(expectedDtos, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(productRepository, times(1)).findByNameOrDescriptionContaining("souls");
    }

    //Devuelve lista vacía
    @Test
    public void give_products_exists_based_based_on_name_or_description_get_search_service_return_empty_list() {
        //Given
        when(productRepository.findByNameOrDescriptionContaining("souls")).thenReturn(Collections.emptyList());

        //When
        ResponseEntity<List<ProductDTO>> response = searchProductService.execute("souls");

        //Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(productRepository, times(1)).findByNameOrDescriptionContaining("souls");

    }
}
