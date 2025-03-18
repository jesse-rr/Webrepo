package com.example.auctionhouse;

import com.example.auctionhouse.config.security.EnvPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@EnableScheduling
@PropertySource(value = "classpath:.env", factory = EnvPropertySourceFactory.class)
public class AuctionHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuctionHouseApplication.class, args);
    }
}
