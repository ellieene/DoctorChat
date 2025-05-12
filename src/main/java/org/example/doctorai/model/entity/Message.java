package org.example.doctorai.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    private UUID messageId;

    @Column(name = "role",nullable = false)
    private String role;

    @Column(name = "content", columnDefinition = "text")
    private String content;
}
