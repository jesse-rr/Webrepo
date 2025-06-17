package com.handleGod.config;

import com.handleGod.filter.IpBlockFilter;
import com.handleGod.filter.RateLimitFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class GlobalSecurityConfig {

    private final RateLimitFilter rateLimitFilter;
    private final IpBlockFilter ipBlockFilter;

    @Bean
    @Order(0)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth
                        .anyRequest().authenticated()
                )
                .addFilterBefore(rateLimitFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(ipBlockFilter, BasicAuthenticationFilter.class)
                .formLogin(login -> login
//                        .loginPage("/login")
                        .defaultSuccessUrl("/api/v1/admins", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/api/v1/public")
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .cors(AbstractHttpConfigurer::disable)
                .userDetailsService(inMemoryUserDetailsService());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of(
//                "http://localhost:8080"
//        ));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowCredentials(true);
//        configuration.setMaxAge(3600L);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    @Bean
    public UserDetailsService inMemoryUserDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
