package com.example.multisecurityspring.domain.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "packs")
/*@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Pack.class)*/
public class Pack {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "packs_products",
            joinColumns = @JoinColumn(name = "pack_id"),
            inverseJoinColumns  = @JoinColumn(name = "product_id")
    )
    @Builder.Default
    /*@JsonManagedReference*/
    @JsonIgnoreProperties("packs")
    private Set<Product> products = new HashSet<>();
    /*
     * Helper Method
     */
    public void addProducts(Product p){
        this.products.add(p);
        p.getPacks().add(this);
    }
    public void removeProducts(Product p){
        this.products.remove(p);
        p.getPacks().remove(this);
    }
}
