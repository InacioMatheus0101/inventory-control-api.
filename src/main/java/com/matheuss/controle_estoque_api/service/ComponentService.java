package com.matheuss.controle_estoque_api.service;

import com.matheuss.controle_estoque_api.domain.Component;
import com.matheuss.controle_estoque_api.dto.ComponentCreateDTO;
import com.matheuss.controle_estoque_api.dto.ComponentResponseDTO;
import com.matheuss.controle_estoque_api.dto.ComponentUpdateDTO;
import com.matheuss.controle_estoque_api.mapper.ComponentMapper;
import com.matheuss.controle_estoque_api.repository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComponentService {

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ComponentMapper componentMapper;

    @Transactional
    public ComponentResponseDTO createComponent(ComponentCreateDTO dto) {
        Component newComponent = componentMapper.toEntity(dto);
        Component savedComponent = componentRepository.save(newComponent);
        return componentMapper.toResponseDTO(savedComponent);
    }

    @Transactional(readOnly = true)
    public List<ComponentResponseDTO> findAllComponents() {
        return componentRepository.findAll().stream()
                .map(componentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ComponentResponseDTO> findComponentById(Long id) {
        return componentRepository.findById(id)
                .map(componentMapper::toResponseDTO);
    }

    @Transactional
    public Optional<ComponentResponseDTO> updateComponent(Long id, ComponentUpdateDTO dto) {
        return componentRepository.findById(id).map(existingComponent -> {
            componentMapper.updateEntityFromDto(dto, existingComponent);
            Component updatedComponent = componentRepository.save(existingComponent);
            return componentMapper.toResponseDTO(updatedComponent);
        });
    }

    @Transactional
    public boolean deleteComponent(Long id) {
        if (componentRepository.existsById(id)) {
            componentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
