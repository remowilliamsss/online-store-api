package ru.egorov.onlinestoreapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 128)
    @NotEmpty
    @Column(unique = true)
    private String name;

    @NotEmpty
    private byte[] password;

    private byte[] salt;

    private Boolean isAdmin;

    @JsonIgnore
    @OneToOne(mappedBy = "owner")
    private ShoppingCart cart;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "favorite",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> favorites = new HashSet<>();

    public User(String name, @NotEmpty byte[] password, byte[] salt, Boolean isAdmin) {
        this.name = name;
        this.password = password;
        this.salt = salt;
        this.isAdmin = isAdmin;
    }
}
