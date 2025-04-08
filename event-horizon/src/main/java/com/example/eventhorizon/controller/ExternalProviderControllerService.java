package com.example.eventhorizon.controller;

import com.example.eventhorizon.repository.ExternalProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/providers")
public class ExternalProviderControllerService {

    private final ExternalProviderRepository externalProviderRepository;
}
