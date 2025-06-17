package com.example.cacophony.service;

import com.example.cacophony.dto.request.CommunityRequestDTO;
import com.example.cacophony.dto.request.EmailRequestDTO;
import com.example.cacophony.dto.response.CommunityResponseDTO;
import com.example.cacophony.model.Community;
import com.example.cacophony.model.User;
import com.example.cacophony.repository.CommunityRepository;
import com.example.cacophony.service.helper.GeneralMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.example.cacophony.model.helper.TemplateNames.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final EmailService emailService;
    private final GeneralMapper generalMapper;
    private final UserService userService;

    @PreAuthorize("isAuthenticated() && @userService.isOldEnough(principal.username)")
    public CommunityResponseDTO addCommunity(CommunityRequestDTO requestDTO) {
        log.info("ADDING COMMUNITY :: {}",requestDTO);
        User owner = userService.getUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        emailService.sendEmail(COMMUNITY_CREATION.getName(), new EmailRequestDTO(owner.getEmail(), owner.getUsername()));

        return generalMapper.toCommunityResponseDTO(communityRepository.save(generalMapper.toCommunity(requestDTO, owner)));
    }

    @PreAuthorize("@securityService.isCommunityOwner(principal.username, #communityId) || " +
            "@securityService.hasPermission(principal.username, #communityId, " +
            "T(com.example.cacophony.model.helper.Permission).MANAGE_SERVER)")
    public void deleteCommunity(Long communityId) {
        log.info("REMOVING COMMUNITY :: {}",communityId);
        User owner = userService.getUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        communityRepository.deleteById(communityId);

        emailService.sendEmail(COMMUNITY_DELETION.getName(), new EmailRequestDTO(owner.getEmail(), owner.getUsername()));
    }


    @PreAuthorize("@securityService.isCommunityOwner(principal.username, #communityId) || " +
            "@securityService.hasPermission(principal.username, #communityId, " +
            "T(com.example.cacophony.model.helper.Permission).MANAGE_SERVER)")
    public CommunityResponseDTO updateCommunity(Long communityId, CommunityRequestDTO requestDTO) {
        log.info("UPDATING COMMUNITY :: {}",communityId);
        Community community = getCommunityById(communityId);
        community.setBannerImg(requestDTO.getBannerImg());
        community.setIconImg(requestDTO.getIconImg());
        community.setName(requestDTO.getName());
        community.setDescription(requestDTO.getDescription());

        return generalMapper.toCommunityResponseDTO(community);
    }

    @PreAuthorize("@securityService.hasPermission(principal.username, #communityId, " +
            "T(com.example.cacophony.model.helper.Permission).VIEW_CHANNELS)")
    public Community getCommunityById(Long communityId) {
        return communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException("COMMUNITY NOT FOUND WITH ID :: "+communityId));
    }

    public CommunityResponseDTO getCommunityDTOById(Long communityId) {
        return generalMapper.toCommunityResponseDTO(communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException("COMMUNITY NOT FOUND WITH ID :: "+communityId)));
    }

    public Page<CommunityResponseDTO> getAllCommunities(Pageable pageable) {
        return communityRepository.findAll(pageable)
                .map(generalMapper::toCommunityResponseDTO);
    }
}
