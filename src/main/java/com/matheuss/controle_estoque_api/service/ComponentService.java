package com.matheuss.controle_estoque_api.service;

import com.matheuss.controle_estoque_api.domain.Component;
import com.matheuss.controle_estoque_api.dto.ComponentCreateDTO;
import com.matheuss.controle_estoque_api.dto.ComponentResponseDTO;
import com.matheuss.controle_estoque_api.dto.ComponentUpdateDTO;
import com.matheuss.controle_estoque_api.mapper.ComponentMapper;
import com.matheuss.controle_estoque_api.repository.ComponentRepository;
import jakarta.persistence.EntityNotFoundException; // <<< IMPORT NECESSÁRIO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComponentService {

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ComponentMapper componentMapper;

    // --- CREATE (Permanece o mesmo) ---
    @Transactional
    public ComponentResponseDTO createComponent(ComponentCreateDTO dto) {
        Component newComponent = componentMapper.toEntity(dto);
        Component savedComponent = componentRepository.save(newComponent);
        return componentMapper.toResponseDTO(savedComponent);
    }

    // --- READ (ALL) (Muda o nome para consistência) ---
    @Transactional(readOnly = true)
    public List<ComponentResponseDTO> getAllComponents() {
        return componentRepository.findAll().stream()
                .map(componentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // --- READ (BY ID) (Refatorado para lançar exceção) ---
    @Transactional(readOnly = true)
    public ComponentResponseDTO getComponentById(Long id) {
        return componentRepository.findById(id)
                .map(componentMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Componente não encontrado com o ID: " + id));
    }

    // --- UPDATE (Refatorado para lançar exceção) ---
    @Transactional
    public ComponentResponseDTO updateComponent(Long id, ComponentUpdateDTO dto) {
        Component existingComponent = componentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Componente não encontrado com o ID: " + id));

        componentMapper.updateEntityFromDto(dto, existingComponent);
        Component updatedComponent = componentRepository.save(existingComponent);
        return componentMapper.toResponseDTO(updatedComponent);
    }

    // --- DELETE (Refatorado para ser void e lançar exceção) ---
    @Transactional
    public void deleteComponent(Long id) {
        if (!componentRepository.existsById(id)) {
            throw new EntityNotFoundException("Componente não encontrado com o ID: " + id);
        }
        componentRepository.deleteById(id);
    }
}
