package com.example.cacophony.controlller;

import com.example.cacophony.dto.response.ChannelResponseDTO;
import com.example.cacophony.model.Channel;
import com.example.cacophony.model.Community;
import com.example.cacophony.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/channels")
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping
    public ResponseEntity<ChannelResponseDTO> createChannel(
            @RequestParam String name,
            @RequestParam long messageDelay,
            @RequestParam Long communityId
    ) {
        return ResponseEntity.ok(channelService.addChannel(name, messageDelay, communityId));
    }

    @PutMapping("/{channelId}")
    public ResponseEntity<Void> updateChannel(
            @PathVariable(name = "channelId") Long channelId,
            @RequestParam String name,
            @RequestParam long messageDelay
    ) {
        channelService.updateChannel(channelId, name, messageDelay);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{channelId}")
    public ResponseEntity<Void> deleteChannel(@PathVariable(name = "channelId") Long channelId) {
        channelService.deleteChannel(channelId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{channelId}")
    public ResponseEntity<ChannelResponseDTO> getChannelById(@PathVariable(name = "channelId") Long channelId) {
        return ResponseEntity.ok(channelService.getChannelDTOById(channelId));
    }

    @GetMapping("/{communityId}")
    public ResponseEntity<List<ChannelResponseDTO>> getChannelsByCommunityId(@PathVariable(name = "communityId") Long communityId) {
        return ResponseEntity.ok(channelService.getChannelsByCommunityId(communityId));
    }
}