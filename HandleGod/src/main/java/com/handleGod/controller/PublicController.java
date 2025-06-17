package com.handleGod.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class PublicController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
