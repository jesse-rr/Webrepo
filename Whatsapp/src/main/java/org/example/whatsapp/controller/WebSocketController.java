package org.example.whatsapp.controller;

import lombok.RequiredArgsConstructor;
import org.example.whatsapp.model.Message;
import org.example.whatsapp.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/ws")
public class WebSocketController {

    private final MessageService messageService;

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public void sendMessage(Message message) {
        messageService.saveMetadata(message.getMetadata());
    }
}
