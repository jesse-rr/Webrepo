package com.example.cacophony.service.helper;

import com.example.cacophony.dto.*;
import com.example.cacophony.dto.RoleResponseDTO;
import com.example.cacophony.dto.request.CommunityRequestDTO;
import com.example.cacophony.dto.request.RolesRequestDTO;
import com.example.cacophony.dto.request.UserRequestDTO;
import com.example.cacophony.dto.response.*;
import com.example.cacophony.model.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class GeneralMapper {

    public User toUser(UserRequestDTO userDTO, String passwordHash) {
        return User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .passwordHash(passwordHash)
                .displayName(userDTO.getDisplayName())
                .build();
    }

    public Community toCommunity(CommunityRequestDTO requestDTO, User owner) {
        return Community.builder()
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .bannerImg(requestDTO.getBannerImg())
                .iconImg(requestDTO.getIconImg())
                .owner(owner)
                .build();
    }

    public Role toRole(RolesRequestDTO rolesDTO) {
        return Role.builder()
                .name(rolesDTO.getName())
                .permissions(rolesDTO.getPermissions())
                .color(rolesDTO.getColor())
                .build();
    }

    public Message toMessage(String content, User author, Message replyToId, Channel channel) {
        return Message.builder()
                .content(content)
                .author(author)
                .replyTo(replyToId)
                .channel(channel)
                .build();
    }

    public ChannelResponseDTO toChannelResponseDTO(Channel channel) {
        return ChannelResponseDTO.builder()
                .channelId(channel.getChannelId())
                .name(channel.getName())
                .messageDelay(channel.getMessageDelay())
                .communityId(channel.getCommunity() != null ? channel.getCommunity().getCommunityId() : null)
                .build();
    }

    public MessageResponseDTO toMessageResponseDTO(Message message) {
        return MessageResponseDTO.builder()
                .messageId(message.getMessageId())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .edited(message.isEdited())
                .pinned(message.isPinned())
                .replyToId(message.getReplyTo() != null ? message.getReplyTo().getMessageId() : null)
                .authorUsername(message.getAuthor() != null ? message.getAuthor().getUsername() : null)
                .channelId(message.getChannel() != null ? message.getChannel().getChannelId() : null)
                .replyIds(message.getReplies() != null ?
                        message.getReplies().stream()
                                .map(Message::getMessageId)
                                .collect(Collectors.toSet()) : null)
                .build();
    }

    public CommunityResponseDTO toCommunityResponseDTO(Community community) {
        return CommunityResponseDTO.builder()
                .communityId(community.getCommunityId())
                .name(community.getName())
                .description(community.getDescription())
                .iconImg(community.getIconImg())
                .bannerImg(community.getBannerImg())
                .ownerUsername(community.getOwner() != null ? community.getOwner().getUsername() : null)
                .channelIds(community.getChannels() != null ?
                        community.getChannels().stream()
                                .map(Channel::getChannelId)
                                .collect(Collectors.toSet()) : null)
                .memberUsernames(community.getMembers() != null ?
                        community.getMembers().stream()
                                .map(User::getUsername)
                                .collect(Collectors.toSet()) : null)
                .build();
    }

    public UserResponseDTO toUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .profileImg(user.getProfileImg())
                .status(user.getStatus())
                .bio(user.getBio())
                .communityIds(user.getJoinedCommunities() != null ?
                        user.getJoinedCommunities().stream()
                                .map(Community::getCommunityId)
                                .collect(Collectors.toSet()) : null)
                .roleNames(user.getRoles() != null ?
                        user.getRoles().stream()
                                .map(Role::getName)
                                .collect(Collectors.toSet()) : null)
                .joinedAt(user.getCreatedAt())
                .build();
    }

    public RoleResponseDTO toRoleResponseDTO(Role role) {
        return RoleResponseDTO.builder()
                .roleId(role.getRoleId())
                .name(role.getName())
                .color(role.getColor())
                .permissions(role.getPermissions())
                .communityId(role.getCommunity() != null ? role.getCommunity().getCommunityId() : null)
                .memberUsernames(role.getUsers() != null ?
                        role.getUsers().stream()
                                .map(User::getUsername)
                                .collect(Collectors.toSet()) : null)
                .build();
    }
}
