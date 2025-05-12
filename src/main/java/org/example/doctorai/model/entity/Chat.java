package org.example.doctorai.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.doctorai.model.enums.Doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    private UUID chatId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "model")
    private String model;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "messages")
    private List<Message> messages;

    @Column(name = "doctor")
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Doctor doctor;

    @Column(name = "stream")
    private boolean stream;

    @Column(name = "update_interval")
    private int update_interval;

    public Chat() {
        this.messages = new ArrayList<>();
        this.model = "GigaChat";
        this.update_interval = 0;
        this.stream = false;
    }
}

