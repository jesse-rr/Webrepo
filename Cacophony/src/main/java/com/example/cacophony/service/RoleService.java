package com.example.cacophony.service;

import com.example.cacophony.dto.RoleResponseDTO;
import com.example.cacophony.dto.request.RolesRequestDTO;
import com.example.cacophony.model.Role;
import com.example.cacophony.model.User;
import com.example.cacophony.repository.RoleRepository;
import com.example.cacophony.service.helper.GeneralMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("@userService.isOldEnough(principal.username)")
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserService userService;
    private final GeneralMapper generalMapper;

    @PreAuthorize("@securityService.hasPermission(principal.username, #communityId, " +
            "T(com.example.cacophony.model.helper.Permission).MANAGE_ROLES)")
    public Set<RoleResponseDTO> createRoles(Set<RolesRequestDTO> rolesDTO) {
        log.info("CREATING ROLES :: {}", rolesDTO);
        Set<Role> roles = rolesDTO.stream()
                .map(roleDTO -> roleRepository.save(generalMapper.toRole(roleDTO)))
                .collect(Collectors.toSet());

        return roles.stream()
                .map(generalMapper::toRoleResponseDTO)
                .collect(Collectors.toSet());
    }

    @PreAuthorize("@securityService.hasPermission(principal.username, @roleRepository.findById(#roleIds.iterator().next()).orElseThrow().getCommunity().getCommunityId(), " +
            "T(com.example.cacophony.model.helper.Permission).MANAGE_ROLES)")
    public void deleteRoles(Set<Long> roleIds) {
        log.info("DELETING ROLES :: {}",roleIds);
        roleRepository.deleteAllById(roleIds);
    }

    @PreAuthorize("@securityService.hasPermission(principal.username, @roleRepository.findById(#roleIds.iterator().next()).orElseThrow().getCommunity().getCommunityId(), " +
            "T(com.example.cacophony.model.helper.Permission).MANAGE_ROLES)")
    public void assignRoles(String username, Set<Long> roleIds, boolean isRemove) {
        List<Role> roles = roleRepository.findAllById(roleIds);
        User user = userService.getUserByUsername(username);
        if (isRemove) {
            user.getRoles().removeAll(roles);
        } else {
            user.getRoles().addAll(roles);
        }
    }

    public RoleResponseDTO getRoleById(Long roleId) {
        return generalMapper.toRoleResponseDTO(roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("ROLE NOT FOUND WITH ID :: " + roleId))
        );
    }
}
