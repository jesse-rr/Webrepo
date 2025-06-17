package com.example.cacophony.model;


import com.example.cacophony.annotation.ValidUsername;
import com.example.cacophony.model.helper.BaseEntity;
import com.example.cacophony.model.helper.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Builder
@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true, length = 50) @ValidUsername
    private String username;
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    @Column(nullable = false, length = 100)
    private String passwordHash;
    @Column(name = "display_name", length = 50)
    private String displayName;
    @Column(name = "profile_image")
    private String profileImg;
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ONLINE;
    @Column(length = 500)
    private String bio;

    private LocalDateTime deletionTimestamp;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Message> messages;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Community> communities;

    @ManyToMany
    @JoinTable(name = "community_members",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "community_id"))
    private Set<Community> joinedCommunities;

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
