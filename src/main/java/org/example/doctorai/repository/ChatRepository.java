package org.example.doctorai.repository;

import org.example.doctorai.model.entity.Chat;
import org.example.doctorai.model.enums.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью {@link Chat}
 */
@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {
    List<Chat> findAllByUserIdAndDoctor(UUID userId, Doctor doctor);
}
