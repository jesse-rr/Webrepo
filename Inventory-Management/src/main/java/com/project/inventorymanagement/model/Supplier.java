package com.project.inventorymanagement.model;

import com.project.inventorymanagement.model.helper.Address;
import com.project.inventorymanagement.model.helper.ContactInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@Table(name = "suppliers")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;

    @Column(nullable = false)
    private String name;
    private String logo;
    @Embedded
    private ContactInfo contactInfo;
    @Embedded
    private Address address;


    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;
}
