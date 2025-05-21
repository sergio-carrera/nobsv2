package com.example.nobsv2.product.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity //maps Java class to mySQL
@Data //Java Lombok
@Table(name="product") //definir tabla en mySQL
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;
}
