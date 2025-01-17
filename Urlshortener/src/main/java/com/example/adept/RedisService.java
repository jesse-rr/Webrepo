package com.example.adept;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public String urlShortener(String longUrl, HttpServletRequest request, String alias) {
        String shortUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
        if (alias != null && !redisTemplate.hasKey(alias)) {
            saveUrlToRedis(alias, longUrl);
            return shortUrl + alias;
        }
        String randomString = genRandomString();
        saveUrlToRedis(randomString, longUrl);
        return shortUrl + randomString;
    }

    public String getLongUrl(String alias) {
        return redisTemplate.opsForValue().get(alias);
    }

    private void saveUrlToRedis(String alias, String longUrl) {
        redisTemplate.opsForValue().set(alias,longUrl);
        redisTemplate.expire(alias, 7, TimeUnit.DAYS);
    }

    private String genRandomString() {
        String alphaNumeric = "d3O1dX5xQr7WzB9mVfL0lYpNwIuAk6sT8hGvJ2yKCoF4ZbCjSntHMaP";
        StringBuilder result = new StringBuilder();
        for (int i = 7; i>0; i--) {
            result.append(alphaNumeric.charAt(new Random().nextInt(alphaNumeric.length() - 1)));
        }
        return result.toString();
    }
}
