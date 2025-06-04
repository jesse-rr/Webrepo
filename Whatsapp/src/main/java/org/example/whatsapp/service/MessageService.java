package org.example.whatsapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.whatsapp.model.metadata.MessageMetadata;
import org.example.whatsapp.repository.MessageRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Service
@RequiredArgsConstructor
@RequestMapping("/api/v1/ws")
public class MessageService {

    private final MessageRepository messageRepository;

    public void saveMetadata(MessageMetadata metadata) {
        log.info("SAVING METADATA :: {}",metadata);
        messageRepository.save(metadata);
    }
}
