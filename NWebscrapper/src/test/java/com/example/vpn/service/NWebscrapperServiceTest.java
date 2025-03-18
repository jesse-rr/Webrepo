package com.example.vpn.service;

import com.example.vpn.models.ExportType;
import com.example.vpn.models.ExtractionMethod;
import com.example.vpn.repository.NWebscrapperRepository;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NWebscrapperServiceTest {

    @Mock
    private NWebscrapperRepository webscrapperRepository;

    @InjectMocks
    private NWebscrapperService webscrapperService;

    private String testUrl;
    private List<String> cssSelectors;
    private List<ExtractionMethod> extractions;
    private Map<Boolean, String> outputFile;

    @BeforeEach
    void setUp() {
        testUrl = "https://example.com";
        cssSelectors = Arrays.asList("h1", "p");
        extractions = Arrays.asList(ExtractionMethod.TEXT, ExtractionMethod.ALL);
        outputFile = new HashMap<>();
        outputFile.put(false, "output.txt");
    }

    @Test
    void testScrape_Success() throws IOException {
        // Mock Jsoup to return a predefined document
        try (MockedStatic<Jsoup> mockedJsoup = Mockito.mockStatic(Jsoup.class)) {
            Document mockDocument = mock(Document.class);
            Element mockElementH1 = mock(Element.class);
            Element mockElementP = mock(Element.class);

            when(mockDocument.selectFirst("h1")).thenReturn(mockElementH1);
            when(mockDocument.selectFirst("p")).thenReturn(mockElementP);
            when(mockElementH1.text()).thenReturn("Test Heading");
            when(mockElementP.toString()).thenReturn("<p>Test Paragraph</p>");

            mockedJsoup.when(() -> Jsoup.connect(anyString()))
                    .thenReturn(mock(Connection.class));
            when(Jsoup.connect(anyString()).userAgent(anyString())
                    .followRedirects(false)
                    .get()).thenReturn(mockDocument);

            List<String> result = webscrapperService.scrape(testUrl, cssSelectors, extractions, outputFile, ExportType.TXT);

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals("Test Heading", result.get(0));
            assertEquals("<p>Test Paragraph</p>", result.get(1));
        }
    }

    @Test
    void testScrape_IOException() {
        try (MockedStatic<Jsoup> mockedJsoup = Mockito.mockStatic(Jsoup.class)) {
            mockedJsoup.when(() -> Jsoup.connect(anyString()))
                    .thenThrow(new IOException("Connection failed"));

            assertThrows(RuntimeException.class, () -> {
                webscrapperService.scrape(testUrl, cssSelectors, extractions, outputFile, ExportType.TXT);
            });
        }
    }

    @Test
    void testScrape_SelectorNotFound() throws IOException {
        try (MockedStatic<Jsoup> mockedJsoup = Mockito.mockStatic(Jsoup.class)) {
            Document mockDocument = mock(Document.class);
            when(mockDocument.selectFirst("h1")).thenReturn(null);

            mockedJsoup.when(() -> Jsoup.connect(anyString()))
                    .thenReturn(mock(Connection.class));
            when(Jsoup.connect(anyString()).userAgent(anyString())
                    .followRedirects(false)
                    .get()).thenReturn(mockDocument);

            List<String> result = webscrapperService.scrape(testUrl, cssSelectors, extractions, outputFile, ExportType.TXT);

            assertNotNull(result);
            assertEquals(1, result.size());
        }
    }
}