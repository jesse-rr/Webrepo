package com.handleGod.service;

import com.handleGod.filter.IpBlockFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class IpBlockService {

    private final IpBlockFilter ipBlockFilter;

    public void resetIpBlock() {
        log.info("RESETTING IP BLOCK");
        ipBlockFilter.reset();
    }

    public void toggleIpBlock() {
        log.info("TOGGLING IP BLOCK");
        ipBlockFilter.toggle();
    }

    public boolean isIpBlockEnabled() {
        return ipBlockFilter.isFilterEnabled();
    }
}