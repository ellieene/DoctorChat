package org.example.doctorai.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.doctorai.model.dto.ChatNotificationDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Отправка для уведомления по Kafka
     *
     * @param chatNotificationDTO {@link ChatNotificationDTO}
     */
    @SneakyThrows
    public void sendCreateChat(ChatNotificationDTO chatNotificationDTO) {
        String message = objectMapper.writeValueAsString(chatNotificationDTO);
        kafkaTemplate.send("chat-notification", message);
    }
}