//package com.example.vpn.service;
//
//import com.example.vpn.models.ExportType;
//import com.example.vpn.models.ExtractionMethod;
//import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class NWebscrapperConsumer {
//
//    private final NWebscrapperService webscrapperService;
//
//    @KafkaListener(topics = {"crawler-topic"}, groupId = "webscrapper-group")
//    public List<String> consumeWebscrapperKafkaUrl(
//            String url,
//            List<String> cssSelectors,
//            List<ExtractionMethod> extractions,
//            Map<Boolean, String> outputFile,
//            ExportType exportType
//    ) throws IOException {
//        return webscrapperService.scrape(url, cssSelectors, extractions, outputFile, exportType);
//    }
//}
