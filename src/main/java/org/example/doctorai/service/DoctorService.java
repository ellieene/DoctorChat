package org.example.doctorai.service;


import lombok.RequiredArgsConstructor;
import org.example.doctorai.exception.ChatNotFoundException;
import org.example.doctorai.feignclient.GigaChatClient;
import org.example.doctorai.model.dto.ChatNotificationDTO;
import org.example.doctorai.model.dto.UserDTO;
import org.example.doctorai.model.entity.Chat;
import org.example.doctorai.model.entity.Message;
import org.example.doctorai.model.entity.User;
import org.example.doctorai.model.enums.Doctor;
import org.example.doctorai.model.request.MessageRequest;
import org.example.doctorai.producer.KafkaProducer;
import org.example.doctorai.repository.ChatRepository;
import org.example.doctorai.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Бизнес-логика с доктором
 */
@Service
@RequiredArgsConstructor
public class DoctorService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final GigaChatClient gigaChatClient;
    private final KafkaProducer kafkaProducer;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Создание чата с доктором
     * @param doctor Доктор
     * @param userId ID Пользователя
     * @return UUID
     */
    @Transactional
    public UUID createChat(Doctor doctor, UUID userId) {
        Chat chat = new Chat();
        chat.setDoctor(doctor);
        chat.getMessages().add(createMessage(doctor.getMessage()));
        chat.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден")));
        chatRepository.save(chat);

        if (chat.getUser().isNotification()) {
            sendKafka(doctor, chat.getUser());

        }
        return chat.getChatId();
    }

    /**
     * Настройка чата с GigaChat
     * @param systemMessage Настройка для GigaChat
     * @return {@link Message}
     */
    private Message createMessage(String systemMessage) {
        Message message = new Message();
        message.setRole("system");
        message.setContent(systemMessage);
        return message;
    }

    /**
     * Чат с доктором
     * @param rawBody {@link MessageRequest}
     * @return {@link Message}
     */
    @Transactional
    public Message doctor(MessageRequest rawBody) {
        Chat chat = chatRepository.findById(rawBody.getIdChat())
                .orElseThrow(() -> new ChatNotFoundException("Chat not found"));

        chat.getMessages().add(rawBody.getMessage());
        chat.getMessages()
                .add(gigaChatClient.requestDoctor(chat, customUserDetailsService.getCurrentUser().getId().toString()));
        chatRepository.save(chat);
        return chat.getMessages().getLast();
    }

    /**
     * Вывод чатов с определенным доктором
     * @param userId ID пользователя
     * @param doctor  {@link Doctor}
     * @return Список чатов {@link Chat}
     */
    @Transactional
    public List<Chat> getChatsDoctor(UUID userId, Doctor doctor) {
        return chatRepository.findAllByUserIdAndDoctor(userId, doctor);
    }

    /**
     * Отправка уведомления в kafka
     *
     * @param user   {@link User}
     * @param doctor Название доктора
     */
    private void sendKafka(Doctor doctor, User user) {
        UserDTO userDTO = new UserDTO(user);


        ChatNotificationDTO chatNotificationDTO = ChatNotificationDTO
                .builder()
                .doctor(doctor.getShortName())
                .user(userDTO)
                .build();

        kafkaProducer.sendCreateChat(chatNotificationDTO);
    }
}
