package com.example.auctionhouse.service;

import com.example.auctionhouse.dto.PaymentResponseDTO;
import com.example.auctionhouse.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AccountService accountService;
    private final GeneralMapper mapper;

    // TODO
    public PaymentResponseDTO createPayment(PaymentRequestDTO dto) {
        return null;
    }

    public List<PaymentResponseDTO> getPaymentListByAccount(Long accountId) {
        log.info("GETTING PAYMENT LIST FROM ACCOUNT ID :: {}",accountId);
        return accountService.getAccount(accountId).getPaymentList()
                .stream()
                .map(mapper::toPaymentResponseDTO)
                .collect(Collectors.toList());
    }
}
