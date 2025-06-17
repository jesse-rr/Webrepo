package com.handleGod.controller;

import com.handleGod.model.dto.EndpointInfo;
import com.handleGod.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins")
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public String homepage(Model model) {
        model.addAttribute("endpoints", adminService.getAllUserEndpoints());
        return "index";
    }

    @ResponseBody // RESTFULL CONTROLLER
    @GetMapping("/endpoints")
    public Set<EndpointInfo> getAllUserEndpoints() {
        return adminService.getAllUserEndpoints();
    }
}
