package com.matheuss.controle_estoque_api.service;

import com.matheuss.controle_estoque_api.domain.User;
import com.matheuss.controle_estoque_api.dto.UserCreateDTO;
import com.matheuss.controle_estoque_api.dto.UserResponseDTO;
import com.matheuss.controle_estoque_api.dto.UserUpdateDTO;
import com.matheuss.controle_estoque_api.mapper.UserMapper;
import com.matheuss.controle_estoque_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public UserResponseDTO createUser(UserCreateDTO dto) {
        User newUser = userMapper.toEntity(dto);
        User savedUser = userRepository.save(newUser);
        return userMapper.toResponseDTO(savedUser);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + id));
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserUpdateDTO dto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + id));
        userMapper.updateEntityFromDto(dto, existingUser);
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toResponseDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado com o ID: " + id);
        }
        // Adicionar lógica futura: o que fazer com os ativos de um usuário deletado?
        // Por enquanto, apenas deletamos o usuário.
        userRepository.deleteById(id);
    }
}
