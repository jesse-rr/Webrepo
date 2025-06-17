package com.example.cacophony.service;

import com.example.cacophony.dto.response.ChannelResponseDTO;
import com.example.cacophony.model.Channel;
import com.example.cacophony.model.Community;
import com.example.cacophony.repository.ChannelRepository;
import com.example.cacophony.service.helper.GeneralMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final CommunityService communityService;
    private final GeneralMapper generalMapper;

    @PreAuthorize("@securityService.hasPermission(principal.username, " +
            "#communityId, " +
            "T(com.example.cacophony.model.helper.Permission).MANAGE_CHANNELS)")
    public ChannelResponseDTO addChannel(String name, long messageDelay, Long communityId) {
        Community community = communityService.getCommunityById(communityId);
        log.info("ADDING CHANNEL :: {} TO COMMUNITY  :: {}",name, communityId);

        return generalMapper.toChannelResponseDTO(channelRepository.save(Channel.builder()
                .community(community)
                .name(name)
                .messageDelay(messageDelay)
                .build()));
    }

    @PreAuthorize("@securityService.hasPermission(principal.username, " +
            "@channelRepository.findById(#channelId).orElseThrow().getCommunity().getCommunityId(), " +
            "T(com.example.cacophony.model.helper.Permission).MANAGE_CHANNELS)")
    public ChannelResponseDTO updateChannel(Long channelId, String name, long messageDelay) {
        log.info("UPDATING CHANNEL WITH :: {} - {}",name,messageDelay);

        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException("CHANNEL NOT FOUND WITH NAME :: "+name));
        channel.setName(name);
        channel.setMessageDelay(messageDelay);
        return generalMapper.toChannelResponseDTO(channelRepository.save(channel));
    }

    @PreAuthorize("@securityService.hasPermission(principal.username, " +
            "@channelRepository.findById(#channelId).orElseThrow().getCommunity().getCommunityId(), " +
            "T(com.example.cacophony.model.helper.Permission).MANAGE_CHANNELS)")
    public void deleteChannel(Long channelId) {
        log.info("DELETING CHANNEL WITH ID {}",channelId);
        channelRepository.deleteById(channelId);
    }

    public Channel getChannelById(Long channelId) {
        return channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException("CHANNEL NOT FOUND WITH ID :: "+channelId));
    }

    public ChannelResponseDTO getChannelDTOById(Long channelId) {
        return generalMapper.toChannelResponseDTO(channelRepository.findById(channelId)
                .orElseThrow(() -> new EntityNotFoundException("CHANNEL NOT FOUND WITH ID :: "+channelId)));
    }

    @PreAuthorize("@securityService.hasPermission(principal.usename, #communityId, T(com.example.cacophony.model.helper.Permission).DEFAULT_MEMBER)")
    public List<ChannelResponseDTO> getChannelsByCommunityId(Long communityId) {
        return channelRepository.findChannelsByCommunity_CommunityId(communityId)
                .stream()
                .map(generalMapper::toChannelResponseDTO)
                .toList();
    }

}
