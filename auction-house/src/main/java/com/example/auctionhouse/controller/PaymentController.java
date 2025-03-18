package com.example.auctionhouse.controller;

import com.example.auctionhouse.dto.PaymentResponseDTO;
import com.example.auctionhouse.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{id}/get")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentList(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(paymentService.getPaymentListByAccount(id));
    }
}
