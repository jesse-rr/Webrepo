package com.example.gymmanagement.service;

import com.example.gymmanagement.dto.DiscountDTO;
import com.example.gymmanagement.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountRepository discountRepository;

    public void addDiscount(DiscountDTO discountDTO) {

    }
}
