package org.example.doctorai.feignclient;

import org.example.doctorai.model.entity.Chat;
import org.example.doctorai.model.entity.Message;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Клиент для запроса в GigaChat
 */
@FeignClient(
        name = "GigaChatService",
        url = "${url-gigachat-service}"
)
public interface GigaChatClient {

    @PostMapping
    Message requestDoctor(@RequestBody Chat chat, @RequestParam String UUID);
}
