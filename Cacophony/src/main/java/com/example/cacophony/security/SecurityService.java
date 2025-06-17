package com.example.cacophony.security;

import com.example.cacophony.model.Community;
import com.example.cacophony.model.Message;
import com.example.cacophony.model.Role;
import com.example.cacophony.model.User;
import com.example.cacophony.model.helper.Permission;
import com.example.cacophony.service.CommunityService;
import com.example.cacophony.service.MessageService;
import com.example.cacophony.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final CommunityService communityService;
    private final MessageService messageService;
    private final UserService userService;

    public boolean hasPermission(String username, Long communityId, long permission) {
        Community community = communityService.getCommunityById(communityId);
        User user = userService.getUserByUsername(username);
        return hasPermission(user, community, permission);
    }

    public boolean hasPermission(User user, Community community, long permission) {
        if (community.getOwner().getUsername().equals(user.getUsername())) {
            return true;
        }

        for (Role role : user.getRoles()) {
            if (role.getCommunity().equals(community)) {
                if ((role.getPermissions() & Permission.ADMINISTRATOR) != 0) {
                    return true;
                }
                if ((role.getPermissions() & permission) != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCommunityOwner(String username, Long communityId) {
        Community community = communityService.getCommunityById(communityId);
        return community.getOwner().getUsername().equals(username);
    }

    public boolean isMessageOwner(Long messageId, String username) {
        return messageService.getMessageById(messageId).getAuthor().getUsername().equals(username);
    }
}
