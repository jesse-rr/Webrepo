package com.example.vpn.controller;

import com.example.vpn.models.ExportType;
import com.example.vpn.models.ExtractionMethod;
import com.example.vpn.models.Website;
import com.example.vpn.service.NWebscrapperConsumer;
import com.example.vpn.service.NWebscrapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/scrapper")
public class NWebscrapperController {

    private final NWebscrapperService webscrapperService;
    private final NWebscrapperConsumer webscrapperConsumer;

    @GetMapping("/scrape")
    public ResponseEntity<List<String>> scrapeUrl(
            @RequestParam String url,
            @RequestParam List<String> cssSelectors,
            @RequestParam List<ExtractionMethod> extractions,
            @RequestParam(required = false) Map<Boolean, String> outputFile,
            @RequestParam ExportType exportType
            ) throws IOException {
        return ResponseEntity.ok(webscrapperConsumer.consumeWebscrapperKafkaUrl(url, cssSelectors, extractions, outputFile, exportType));
    }

    @GetMapping("/scrape/csv")
    public ResponseEntity<Void> scrapeUrlCSV(
            @RequestParam String url,
            @RequestParam List<String> cssSelectors,
            @RequestParam List<ExtractionMethod> extractions,
            @RequestParam(required = false) Map<Boolean, String> outputFile,
            @RequestParam ExportType exportType,
            @RequestParam List<String> recordNames
    ) throws IOException {
        webscrapperConsumer.consumeWebscrapperKafkaUrl(url, cssSelectors, extractions, outputFile, exportType);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-url")
    public ResponseEntity<Void> addUrlToScrapperDB(
            @RequestParam List<String> urls,
            @RequestParam(required = false) Set<String> pages
    ) {
        webscrapperService.addUrlToScrapperDB(urls, pages);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-url")
    public ResponseEntity<Void> deleteUrlFromScrapperDB(
            @RequestParam List<String> urls,
            @RequestBody Website website
    ) {
        webscrapperService.deleteUrlFromScrapperDB(urls);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/get-pages")
    public ResponseEntity<Void> getPagesFromUrl(
            @RequestParam List<String> urls
    ) {
        webscrapperService.getPagesFromUrl(urls);
        return ResponseEntity.ok().build();
    }
}
