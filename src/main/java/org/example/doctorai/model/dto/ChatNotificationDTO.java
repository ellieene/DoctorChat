package org.example.doctorai.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatNotificationDTO {

    private UserDTO user;
    private String doctor;

}
