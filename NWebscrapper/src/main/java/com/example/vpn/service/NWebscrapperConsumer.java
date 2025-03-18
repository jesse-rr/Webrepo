package com.example.vpn.service;

import com.example.vpn.models.ExtractionMethod;
import com.example.vpn.models.ParseType;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NWebscrapperConsumer {

    private final NWebscrapperService webscrapperService;

    @KafkaListener(topics = {"crawler-topic"}, groupId = "webscrapper-group")
    public List<String> consumeWebscrapperKafkaUrl(String url, List<String> cssSelectors, List<ExtractionMethod> extractions, Boolean isExport, String f_name, ParseType parseType) {
        return webscrapperService.scrape(url, cssSelectors, extractions, isExport, f_name, parseType);
    }
}
