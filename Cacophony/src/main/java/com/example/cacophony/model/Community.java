package com.example.cacophony.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@Entity
@Table(name = "channels")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityId;

    @Column(nullable = false, length = 50)
    private String name;
    @Column(length = 500)
    private String description;
    @Column(name = "icon_image")
    private String iconImg;
    @Column(name = "banner_image")
    private String bannerImg;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_community", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Channel> channels;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Role> roles;

    @ManyToMany(mappedBy = "joinedCommunities", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> members;

}
