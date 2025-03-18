package com.example.vpn;

import com.example.vpn.models.ExportType;
import com.example.vpn.models.ExtractionMethod;
import com.example.vpn.service.NWebscrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@EnableAsync
@EnableKafka
@SpringBootApplication
public class NWebscrapperApplication implements CommandLineRunner {

    @Autowired
    private NWebscrapperService nWebscrapperService;

    public static void main(String[] args) {
        SpringApplication.run(NWebscrapperApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        var hash = new HashMap<Boolean, String>();
        hash.put(false, "dosent-matter");
        nWebscrapperService.scrape("https://www.google.com/", List.of("body"), Collections.singletonList(ExtractionMethod.ALL), hash, ExportType.TXT);
    }
}
