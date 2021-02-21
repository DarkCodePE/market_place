package com.example.multisecurityspring.persistence.entity;

import javax.persistence.*;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private float price;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
}
