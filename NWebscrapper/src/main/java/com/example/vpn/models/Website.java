package com.example.vpn.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity(name = "website")
@Table(name = "website")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Website {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String url;
    @ElementCollection
    private Set<String> pages;
    private boolean isWorking;
    private String notes;
}
