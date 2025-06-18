package com.project.inventorymanagement.model;

import com.project.inventorymanagement.model.helper.Address;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@Table(name = "locations")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @Column(nullable = false)
    private String name;
    @Embedded
    private Address address;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Inventory> inventoryRecords;
}
