package com.example.cacophony.service;

import com.example.cacophony.dto.response.MessageResponseDTO;
import com.example.cacophony.model.Channel;
import com.example.cacophony.model.Message;
import com.example.cacophony.model.User;
import com.example.cacophony.repository.MessageRepository;
import com.example.cacophony.service.helper.GeneralMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChannelService channelService;
    private final UserService userService;
    private final GeneralMapper generalMapper;

    @PreAuthorize("isAuthenticated()")
    public MessageResponseDTO createMessage(String content, String username, Long channelId, Long replyToId) {
        User user = userService.getUserByUsername(username);
        Channel channel = channelService.getChannelById(channelId);
        Message replyTo = null;
        if (replyToId != null) {
            replyTo = getMessageById(replyToId);
        }

        return generalMapper.toMessageResponseDTO(messageRepository.save(generalMapper.toMessage(content, user, replyTo, channel)));
    }

    @PreAuthorize("@securityService.isMessageOwner(messageId, principal.username)")
    public MessageResponseDTO updateMessage(Long messageId, String newContent) {
        Message message = getMessageById(messageId);
        message.setContent(newContent);
        message.setEdited(true);
        message.setTimestamp(LocalDateTime.now());

        return generalMapper.toMessageResponseDTO(messageRepository.save(message));
    }

    @PreAuthorize("@securityService.isMessageOwner(messageId, principal.username)")
    public void deleteMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }

    @PreAuthorize("@securityService.hasPermission(principal.username, " +
            "@securityService.hasPermission(principal.username, @communityRepository.findById(@messageRepository.findById(#messageId).orElseThrow().getChannel().getCommunity().getCommunityId())), " +
            "T(com.example.cacophony.model.helper.Permission).MANAGE_MESSAGES)")
    public void pinMessage(Long messageId) {
        Message message = getMessageById(messageId);
        message.setPinned(!message.isPinned());
        message.setTimestamp(LocalDateTime.now());

        messageRepository.save(message);
    }

    public Message getMessageById(Long messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new EntityNotFoundException("MESSAGE NOT FOUND WITH ID :: " + messageId));
    }
}
