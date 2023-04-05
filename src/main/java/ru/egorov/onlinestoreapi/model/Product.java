package ru.egorov.onlinestoreapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Product {

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

    @JsonIgnore
    @ManyToMany(mappedBy = "favorites")
    private Set<User> likes = new HashSet<>();
}
