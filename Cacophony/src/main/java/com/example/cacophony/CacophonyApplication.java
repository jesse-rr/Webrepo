package com.example.cacophony;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableAsync
@EnableScheduling
@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true)
public class CacophonyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CacophonyApplication.class, args);
    }

}
