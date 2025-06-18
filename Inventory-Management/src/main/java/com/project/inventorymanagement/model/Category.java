package com.project.inventorymanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@Table(name = "categories")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false, unique = true)
    private String name;
    @Column(length = 500)
    private String description;

    @ManyToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;
}
