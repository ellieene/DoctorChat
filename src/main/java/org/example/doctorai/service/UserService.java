package org.example.doctorai.service;

import lombok.RequiredArgsConstructor;
import org.example.doctorai.model.dto.UserDTO;
import org.example.doctorai.model.enums.Role;
import org.example.doctorai.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Бизнес-логика для {@link UserDTO}
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Получение всех {@link UserDTO} для рассылки новостей
     * @return List {@link UserDTO}
     */
    @Transactional
    public List<UserDTO> getUsers(){
        return userRepository.findByRoleAndLetterIsTrue(Role.ROLE_USER)
                .stream()
                .map(UserDTO::new)
                .toList();
    }

}
