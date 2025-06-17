package com.example.cacophony.controlller;

import com.example.cacophony.dto.RoleResponseDTO;
import com.example.cacophony.dto.request.RolesRequestDTO;
import com.example.cacophony.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Set<RoleResponseDTO>> createRoles(@RequestBody @Valid Set<RolesRequestDTO> rolesDTO) {
        return ResponseEntity.ok(roleService.createRoles(rolesDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRoles(@RequestParam Set<Long> roleIds) {
        roleService.deleteRoles(roleIds);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/assign")
    public ResponseEntity<Void> assignRoles(
            @RequestParam String username,
            @RequestParam Set<Long> roleIds,
            @RequestParam(required = false) boolean isRemove
    ) {
        roleService.assignRoles(username, roleIds, isRemove);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable(name = "roleId") Long roleId) {
        return ResponseEntity.ok(roleService.getRoleById(roleId));
    }
}