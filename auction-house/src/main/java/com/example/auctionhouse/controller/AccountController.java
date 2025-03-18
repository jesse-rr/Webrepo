package com.example.auctionhouse.controller;

import com.example.auctionhouse.dto.AccountParamsRequest;
import com.example.auctionhouse.model.Account;
import com.example.auctionhouse.service.AccountService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @PutMapping("/{id}/settings")
    public ResponseEntity<Account> updateAccountParams(
            @PathVariable(value = "id") Long id,
            @RequestBody AccountParamsRequest request
    ) {
        return ResponseEntity.ok(accountService.updateAccountParams(id, request));
    }

    @GetMapping("/{id}/get")
    public ResponseEntity<Account> getAccount(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteAccount(
            @PathVariable Long id,
            @RequestParam String passwd
    ) throws MessagingException {
        accountService.deleteAccount(id, passwd);
        return ResponseEntity.noContent().build();
    }

}
