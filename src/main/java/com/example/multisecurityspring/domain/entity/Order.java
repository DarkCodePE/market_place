package com.example.multisecurityspring.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @CreatedDate
    @Column(name = "date_purchase")
    private LocalDateTime datePurchase;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Builder.Default
    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderLine> lines = new HashSet<>();

    public float getTotal(){
        return (float) lines.stream()
                .mapToDouble(OrderLine::getSubtotal)
                .sum();
    }
    //necesitamos metodos helpers pues es una relacion bidireccional
    /*
     * Helpers
     */
    public void addOrderLine(OrderLine line){
        lines.add(line);
        line.setOrder(this);
    }
    public void removeOrderLine(OrderLine line){
        lines.remove(line);
        line.setOrder(null);
    }
}
