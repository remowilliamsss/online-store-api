package ru.egorov.onlinestoreapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    private String name;

    private String category;

    private String subcategory;

    private Double price;

    private Integer amount;

    private String image;

    @ManyToMany(mappedBy = "favorites")
    private Set<User> likes;
}
