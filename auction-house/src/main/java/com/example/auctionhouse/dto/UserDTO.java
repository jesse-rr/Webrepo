package com.example.auctionhouse.dto;

public record UserDTO(
        String firstname,
        String lastname,
        String email,
        String password,
        String cpfCnpj,
        String phone
) {
}
