package com.example.nobsv2.product;

import com.example.nobsv2.product.model.Product;
import com.example.nobsv2.product.model.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    //Spring Data JPA
    List<Product> findByNameContaining(String keyword);

    //JPQL
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword%")
    List<Product> findByNameOrDescriptionContaining(@Param("keyword") String name);

    //native query
    @Query(value = "SELECT * FROM Product WHERE name LIKE %:keyword% OR description LIKE %:keyword%", nativeQuery = true)
    List<Product> findByNameOrDescriptionContainingNative(@Param("keyword") String keyword);
}
