package com.example.adept;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequiredArgsConstructor
public class UrlShortenerController {

    private final RedisService redisService;

    @RequestMapping(value = "/api/v1/url-shortener", method = POST)
    public ResponseEntity<String> urlShortener(
            @RequestParam String longUrl,
            @RequestParam(required = false) String alias,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(redisService.urlShortener(longUrl, request, alias));
    }

    @GetMapping("/{alias}")
    public void redirect(
            HttpServletResponse response,
            @PathVariable(value = "alias") String alias
    ) {
        try {
            response.sendRedirect(redisService.getLongUrl(alias));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
