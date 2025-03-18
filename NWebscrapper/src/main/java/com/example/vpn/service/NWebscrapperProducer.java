//package com.example.vpn.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class NWebscrapperProducer {
//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//
//    public void sendWebscrappingTask(String url) {
//        kafkaTemplate.send("crawler-topic", url);
//    }
//}
