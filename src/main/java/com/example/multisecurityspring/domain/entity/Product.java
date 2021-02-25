package com.example.multisecurityspring.domain.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
/*@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Product.class )*/
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private float price;
    private String image;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "products")
    @Builder.Default
   /* @JsonBackReference*/
    @JsonIgnoreProperties("products")
    private Set<Pack> packs = new HashSet<>();
}
