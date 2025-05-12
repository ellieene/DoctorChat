package org.example.doctorai.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.example.doctorai.model.entity.Message;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class AnswerGigaChatMessageRequest {
    private List<ChoiceRequest> choices;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Setter
    @Getter
    public static class ChoiceRequest {
        private Message message;

    }
}

