package com.example.cacophony.controlller;

import com.example.cacophony.dto.request.MessageRequestDTO;
import com.example.cacophony.dto.response.MessageResponseDTO;
import com.example.cacophony.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketMessageController {

    private final MessageService messageService;

    @MessageMapping("/messages/{channelId}/send")
    @SendTo("/topic/messages/{channelId}")
    public ResponseEntity<MessageResponseDTO> handleSendMessage(
            @DestinationVariable Long channelId,
            @Payload MessageRequestDTO request
    ) {
        return ResponseEntity.ok(messageService.createMessage(
                request.getContent(),
                request.getUsername(),
                channelId,
                request.getReplyToId()
        ));
    }

    @MessageMapping("/messages/{messageId}/update")
    @SendTo("/topic/messages/update/{channelId}")
    public ResponseEntity<MessageResponseDTO> handleUpdateMessage(
            @DestinationVariable Long messageId,
            @Payload String newContent
    ) {
        return ResponseEntity.ok(messageService.updateMessage(messageId, newContent));
    }

    @MessageMapping("/messages/{messageId}/delete")
    @SendTo("/topic/messages/delete/{channelId}")
    public ResponseEntity<Void> handleDeleteMessage(@DestinationVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.noContent().build();
    }

    @MessageMapping("/messages/{messageId}/pin")
    @SendTo("/topic/messages/pin/{channelId}")
    public ResponseEntity<Void> handlePinMessage(@DestinationVariable Long messageId) {
        messageService.pinMessage(messageId);
        return ResponseEntity.ok().build();
    }
}