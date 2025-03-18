package com.example.vpn.service;

import com.example.vpn.config.ExtractionFactory;
import com.example.vpn.models.ExportType;
import com.example.vpn.models.ExtractionMethod;
import com.example.vpn.models.Website;
import com.example.vpn.repository.NWebscrapperRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
public class NWebscrapperService {

    private final NWebscrapperRepository webscrapperRepository;

    public void addUrlToScrapperDB(List<String> urls, Set<String> pages) {
        urls.forEach(url -> {
            Website website = webscrapperRepository.findByUrl(url).orElse(null);
            if (website!=null) {
                website.setPages(pages);
                webscrapperRepository.save(website);
            } else {
                Website saveWebsite = Website.builder()
                        .url(url)
                        .isWorking(isUrlWorking(url))
                        .pages(pages)
                        .notes("USE THIS TO DEFINE WHICH DATA IS OBTAINED FROM THIS SITE, ERRORS OR JUST SIMPLE NOTES...")
                        .build();
                webscrapperRepository.save(saveWebsite);
            }
        });
    }

    @Async
    public boolean isUrlWorking(String site_url) {
        try {
            URL url = new URL(site_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int httpResponse = connection.getResponseCode();
            return (httpResponse < 400) ? true : false;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUrlFromScrapperDB(List<String> urls) {
        urls.forEach(webscrapperRepository::deleteByUrl);
    }

    public void getPagesFromUrl(List<String> urls) {
        List<Set<String>> fullPages = new ArrayList<>();
        for (String url : urls) {
            Set<String> pages = new HashSet<>();
            try {
                Document document = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
                        .followRedirects(false)
                        .get();
                Elements links = document.select("a[href]");

                for (Element link : links) {
                    if (!isUrlUnnecessary(link.attr("href"))) {
                        String parsedUrl = parseUrl(link.attr("href"), url);
                        pages.add(parsedUrl);
                    }
                }
            } catch (Exception e) {
            }
            fullPages.add(pages);
            for (Set<String> pageList : fullPages) {
                addUrlToScrapperDB(List.of(url), pageList);
            }
        }
    }

    public List<String> scrape(String url, List<String> cssSelectors, List<ExtractionMethod> extractions, Map<Boolean, String> outputFile, ExportType exportType) throws IOException{
        List<String> extractedResponse = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
                    .followRedirects(false)
                    .get();
            for (int i=0; i<cssSelectors.size(); i++) {
                Element selector = document.selectFirst(cssSelectors.get(i));
                if (selector == null) {
                    log.warn("SELECTOR :: {} :: NOT FOUND", cssSelectors.get(i));
                    continue;
                }
                ExtractionMethod extractionMethod = (i < extractions.size()) ? extractions.get(i) : ExtractionMethod.ALL;
                switch (extractionMethod) {
                    case ALL:
                        extractedResponse.add(String.valueOf(selector));
                        break;
                    case TEXT:
                        extractedResponse.add(selector.text());
                        break;
                    case USER_CHOICE:
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("ENTER EXTRACTION-METHOD :: ");
                        extractedResponse.add(selector.attr(scanner.nextLine()));
                        break;
                    default:
                        extractedResponse.add(selector.attr(extractions.get(i).getValue()));
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (outputFile.containsKey(true)) {
            outputAsFile(extractedResponse, outputFile.get(true), exportType);
        }
        return extractedResponse;
    }

    public void outputAsFile(List<String> params, String fileName, ExportType exportType) throws IOException{
        ExtractionFactory factory = new ExtractionFactory();
        switch (exportType) {
            case TXT:
                factory.parseToTXT(params, fileName);
                break;
            case CSV:
                // TODO MAKE CONTROLLER LATER, HAS TO BE DIFFERENT METHOD
                factory.parseToCSV(params, fileName, params);
                break;
            case JSON:
                // TODO SAME AS ABOVE
//                factory.parseToJSON(params, fileName);
                break;
            default:
                throw new RuntimeException("NOT VALID OPTION OF EXPORTATION");
        }
    }

    private boolean isUrlUnnecessary(String url) {
        // Broken links, events, tracking params, captcha
        if (url.startsWith("#") || url.contains("javascript") || url.contains("ref=") || url.contains("utm_source=") || url.contains("captcha")) {
            return true;
        }
        return false;
    }

    private String parseUrl(String url, String initialUrl) throws MalformedURLException {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return url;
        }
        URL parsedInitialUrl = new URL(initialUrl);
        URL resolvedUrl = new URL(parsedInitialUrl, url);
        return resolvedUrl.toString();
    }
}
