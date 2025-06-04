package com.example.gymmanagement.model;

import com.example.gymmanagement.model.enums.BaseEntity;
import com.example.gymmanagement.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 30)
    private String firstname;
    @Column(nullable = false, length = 30)
    private String lastname;
    @Column(nullable = false, length = 11)
    private String cpf;
    @Email @Column(length = 50, nullable = false, unique = true)
    private String email;
    @Column(nullable = false, length = 50)
    private String password;
    private String phone;

    private LocalDateTime deletionTimestamp;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "plan_user")
    private PaymentPlan plan;

    @OneToMany(mappedBy = "user")
    private Set<Payment> payments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
