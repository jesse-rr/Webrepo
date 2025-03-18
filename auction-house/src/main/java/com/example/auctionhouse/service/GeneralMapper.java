package com.example.auctionhouse.service;

import com.example.auctionhouse.dto.PaymentResponseDTO;
import com.example.auctionhouse.dto.UserDTO;
import com.example.auctionhouse.model.Payment;
import com.example.auctionhouse.model.User;
import org.springframework.stereotype.Service;

@Service
public class GeneralMapper {

    public User toUser(UserDTO dto, String encodedPasswd) {
        return User.builder()
                .firstname(dto.firstname())
                .lastname(dto.lastname())
                .email(dto.email())
                .cpfCnpj(dto.cpfCnpj())
                .password(encodedPasswd)
                .phone(dto.phone())
                .build();
    }

    public PaymentResponseDTO toPaymentResponseDTO(Payment payment) {
        return new PaymentResponseDTO(
                payment.getInitialAmount(),
                payment.getFinalAmount(),
                payment.getCurrency(),
                payment.getPaymentStatus(),
                payment.getDescription(),
                payment.getDiscounts()
        );
    }
}
