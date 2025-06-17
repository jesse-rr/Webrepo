package com.example.cacophony.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@Entity
@Table(name = "roles")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(nullable = false, length = 40)
    private String name;
    @Column(nullable = false)
    private String color;
    @Column(nullable = false, name = "permissions_bitmask")
    private long permissions; // combined. bitmask permissions, (ex: 2 + 4 = 6)

    @ManyToOne
    @JoinColumn(name = "community_roles", nullable = false)
    private Community community;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> users;

}