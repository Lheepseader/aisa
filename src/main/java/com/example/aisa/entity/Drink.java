package com.example.aisa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "drinks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "drink_name")
    private String drinkName;

    @Column(name = "price")
    private Integer price;

    @Column(name = "order_count")
    private Long orderCount = 0L;

    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<RecipeElement> recipeElements;

    public void incrementOrderCount() {
        this.orderCount++;
    }


    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Drink drink = (Drink) o;
        return Objects.equals(id, drink.id);
    }

    @Override public int hashCode() {
        return Objects.hash(id);
    }
}
