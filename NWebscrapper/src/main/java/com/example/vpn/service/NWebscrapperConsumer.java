package com.example.vpn.service;

import com.example.vpn.models.ExtractionMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NWebscrapperConsumer {

    private final NWebscrapperService webscrapperService;

    @KafkaListener(topics = {"crawler-topic"}, groupId = "webscrapper-group")
    public void consumeWebscrapperKafkaUrl(String url, List<String> cssSelectors, List<ExtractionMethod> extractions, boolean isOutput) {
        webscrapperService.scrape(url, cssSelectors, extractions, isOutput);
    }
}
