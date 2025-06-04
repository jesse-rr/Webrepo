package org.example.whatsapp.model.metadata;

import org.example.whatsapp.model.enums.MessageStatus;
import org.example.whatsapp.model.enums.MessageType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Document(collection = "message_metadata")
public class MessageMetadata {
    @Id
    private String messageId;
    private String senderHash;
    private Set<String> receiversHash;
    private Date timestamp;
    private MessageType messageType;
    private MessageStatus messageStatus;
    private int sizeKb;
    private boolean e2ee;
}
