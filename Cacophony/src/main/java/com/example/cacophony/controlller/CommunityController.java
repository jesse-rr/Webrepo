package com.example.cacophony.controlller;

import com.example.cacophony.dto.request.CommunityRequestDTO;
import com.example.cacophony.dto.response.CommunityResponseDTO;
import com.example.cacophony.model.Community;
import com.example.cacophony.service.CommunityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/communities")
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping
    public ResponseEntity<Void> createCommunity(@RequestBody @Valid CommunityRequestDTO requestDTO) {
        communityService.addCommunity(requestDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{communityId}")
    public ResponseEntity<Void> updateCommunity(
            @PathVariable Long communityId,
            @RequestBody @Valid CommunityRequestDTO requestDTO
    ) {
        communityService.updateCommunity(communityId, requestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{communityId}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable(name = "communityId") Long communityId) {
        communityService.deleteCommunity(communityId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{communityId}")
    public ResponseEntity<CommunityResponseDTO> getCommunity(@PathVariable(name = "communityId") Long communityId) {
        return ResponseEntity.ok(communityService.getCommunityDTOById(communityId));
    }
}