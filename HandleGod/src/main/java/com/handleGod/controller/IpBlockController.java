package com.handleGod.controller;

import com.handleGod.service.IpBlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins/ip-block")
public class IpBlockController {

    private final IpBlockService ipBlockService;

    @PostMapping("/toggle")
    public void toggleIpBlock() {
        ipBlockService.toggleIpBlock();
    }

    @PostMapping("/reset")
    public void resetIpBlock() {
        ipBlockService.resetIpBlock();
    }

    @GetMapping("/enabled")
    public boolean isIpBlockEnabled() {
        return ipBlockService.isIpBlockEnabled();
    }
}