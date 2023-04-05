package ru.egorov.onlinestoreapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class ShoppingCart {

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY)
    private Set<CartProduct> products = new HashSet<>();
}
