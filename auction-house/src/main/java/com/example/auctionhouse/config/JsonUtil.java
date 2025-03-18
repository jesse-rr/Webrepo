package com.example.auctionhouse.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JsonUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> jsonToMap(String json) {
        try {
            return objectMapper.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("FAILED TO TRANSFORM JSON TO MAP :: USER CONFIGS :: "+e);
        }
    }

    public static String mapToJson(Map<String, Object> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("FAILED TO TRANSFORM MAP TO STRING :: USER CONFIGS :: "+e);
        }
    }

    public static String loadJsonContent(String path) {
        try {
            return new String(Files.readAllBytes(new File(path).toPath()));
        } catch (IOException e) {
            throw new RuntimeException("FAILED TO LOAD JSON CONTENT :: "+e);
        }
    }
}
