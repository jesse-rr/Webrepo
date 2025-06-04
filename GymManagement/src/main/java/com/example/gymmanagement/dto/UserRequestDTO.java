package com.example.gymmanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record UserRequestDTO(
    @NotNull String firstname,
    @NotNull String lastname,
    @NotNull @CPF String cpf,
    @NotNull @Email String email,
    @NotNull char[] password,
    String phone
) {
}
