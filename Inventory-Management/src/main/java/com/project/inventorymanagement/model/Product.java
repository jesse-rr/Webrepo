package com.project.inventorymanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Entity
@Table(name = "products")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, unique = true)
    private String SKU;
    @Column(nullable = false)
    private String name;
    @Column(length = 500)
    private String description;
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_product", nullable = false)
    private List<Category> categories;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Inventory> inventories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;
}
