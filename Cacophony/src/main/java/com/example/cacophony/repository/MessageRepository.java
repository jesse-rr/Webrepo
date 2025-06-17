package com.example.cacophony.repository;

import com.example.cacophony.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
