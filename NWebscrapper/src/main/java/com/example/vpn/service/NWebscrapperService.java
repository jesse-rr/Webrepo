package com.example.vpn.service;

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
import java.net.URL;
import java.util.*;


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
                Website.builder()
                        .url(url)
                        .isWorking(isUrlWorking(url))
                        .pages(pages)
                        .notes("USE THIS TO DEFINE WHICH DATA IS OBTAINED FROM THIS SITE, ERRORS OR JUST SIMPLE NOTES...")
                        .build();
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
                Document document = Jsoup.connect(url).get();
                Elements links = document.select("a[href]");

                for (Element link : links) {
                    pages.add(link.attr("href"));
                }
            } catch (Exception e) {
            }
            fullPages.add(pages);
            for (Set<String> pageList : fullPages) {
                addUrlToScrapperDB(List.of(url), pageList);
            }
        }
    }

    public void scrape(String url, List<String> cssSelectors, List<ExtractionMethod> extractions, boolean isOutput) {
        List<String> extractedResponse = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
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
                    default:
                        extractedResponse.add(selector.attr(extractions.get(i).getValue()));
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (isOutput) {
            outputAsFile(extractedResponse);
        }
    }

    public void outputAsFile(List<String> params) {
        try {
            FileWriter writer = new FileWriter("/home/jrr/output"+Math.abs(Math.random()*1000)+".txt");
            for (int i=0; i<params.size(); i++) {
                writer.write(params.get(i)+"\n");
            }
            writer.close();
        } catch (IOException e) {
        }
    }

}
