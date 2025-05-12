package org.example.doctorai.model.request;

import lombok.Getter;
import lombok.Setter;
import org.example.doctorai.model.entity.Message;

import java.util.UUID;

@Getter
@Setter
public class MessageRequest {
    private UUID idChat;
    private Message message;
}
