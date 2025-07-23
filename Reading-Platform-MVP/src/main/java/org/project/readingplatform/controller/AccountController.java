package org.project.readingplatform.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.readingplatform.dto.AccountDTO;
import org.project.readingplatform.models.enums.AccountChoice;
import org.project.readingplatform.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/alter")
    public ResponseEntity<String> alterAccount(
            @RequestBody @Valid AccountDTO request
    ) {
        accountService.alterAccount(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/options/{book-id}")
    public ResponseEntity<Void> accountOptions(
            @PathVariable("book-id") Long bookId,
            @RequestParam UUID accountUUID,
            @RequestParam AccountChoice choice
    ) {
        accountService.accountOptions(bookId, accountUUID, choice);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAccount(
            @RequestParam UUID accountUUID
    ) throws MessagingException {
        accountService.deleteAccount(accountUUID);
        return ResponseEntity.noContent().build();
    }
}
