package com.cnx.ecom.inventory.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    private Long id;

    private String name;

    private String description;

    private String category;

    private String brand;

    @Column(name="image_url")
    private String imageUrl;

    private Double price;

    @Column(name="stock_quantity")
    private Integer stockQuantity;

    private String color;

    private String size;

    private Double weight;
}
