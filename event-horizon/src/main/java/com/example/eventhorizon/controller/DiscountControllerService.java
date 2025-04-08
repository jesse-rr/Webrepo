package com.example.eventhorizon.controller;

import com.example.eventhorizon.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/discounts")
public class DiscountControllerService {

    private final DiscountRepository discountRepository;

}
