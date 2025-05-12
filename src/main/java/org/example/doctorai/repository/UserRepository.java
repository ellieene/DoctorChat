package org.example.doctorai.repository;

import org.example.doctorai.model.entity.User;
import org.example.doctorai.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью {@link User}
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID id);

    List<User> findByRoleAndLetterIsTrue(Role role);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

    boolean existsById(UUID id);

}
