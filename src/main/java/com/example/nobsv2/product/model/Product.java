package com.example.nobsv2.product.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity //maps Java class to mySQL
@Data //Java Lombok
@Table(name="product") //definir tabla en mySQL
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "Name is required")
    @Column(name = "name")
    private String name;

    @Size(min = 20, message = "Description must be at least of 20 characters")
    @Column(name = "description")
    private String description;

    @PositiveOrZero(message = "Price must be zero or positive")
    @Column(name = "price")
    private Double price;
}
