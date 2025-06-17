package com.example.cacophony.repository;

import com.example.cacophony.model.Channel;
import com.example.cacophony.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Optional<Channel> findByName(String name);

    List<Channel> findChannelsByCommunity_CommunityId(Long communityId);
}
