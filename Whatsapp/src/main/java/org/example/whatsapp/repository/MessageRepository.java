package org.example.whatsapp.repository;

import org.example.whatsapp.model.metadata.MessageMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageRepository extends MongoRepository<MessageMetadata, String> {

}
