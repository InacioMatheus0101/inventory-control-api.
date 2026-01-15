package com.matheuss.controle_estoque_api.service;

import com.matheuss.controle_estoque_api.domain.Component;
import com.matheuss.controle_estoque_api.domain.history.HistoryEventType; // <-- IMPORT ADICIONADO
import com.matheuss.controle_estoque_api.dto.ComponentCreateDTO;
import com.matheuss.controle_estoque_api.dto.ComponentResponseDTO;
import com.matheuss.controle_estoque_api.dto.ComponentUpdateDTO;
import com.matheuss.controle_estoque_api.mapper.ComponentMapper;
import com.matheuss.controle_estoque_api.repository.ComponentRepository;
import jakarta.persistence.EntityNotFoundException;
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

    // ====================================================================
    // == PASSO 1: INJETAR O SERVIÇO DE HISTÓRICO ==
    // ====================================================================
    @Autowired
    private AssetHistoryService assetHistoryService;

    @Transactional
    public ComponentResponseDTO createComponent(ComponentCreateDTO dto) {
        Component newComponent = componentMapper.toEntity(dto);
        
        // Salva o novo componente no banco para que ele tenha um ID
        Component savedComponent = componentRepository.save(newComponent);

        // ====================================================================
        // == PASSO 2: REGISTRAR O EVENTO DE CRIAÇÃO NO HISTÓRICO ==
        // ====================================================================
        assetHistoryService.registerEvent(savedComponent, HistoryEventType.CRIACAO, "Ativo cadastrado no sistema.", null);

        return componentMapper.toResponseDTO(savedComponent);
    }

    @Transactional(readOnly = true)
    public List<ComponentResponseDTO> getAllComponents() {
        return componentRepository.findAll().stream()
                .map(componentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ComponentResponseDTO getComponentById(Long id) {
        return componentRepository.findById(id)
                .map(componentMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Componente não encontrado com o ID: " + id));
    }

    @Transactional
    public ComponentResponseDTO updateComponent(Long id, ComponentUpdateDTO dto) {
        Component existingComponent = componentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Componente não encontrado com o ID: " + id));

        componentMapper.updateEntityFromDto(dto, existingComponent);
        Component updatedComponent = componentRepository.save(existingComponent);
        return componentMapper.toResponseDTO(updatedComponent);
    }

    @Transactional
    public void deleteComponent(Long id) {
        if (!componentRepository.existsById(id)) {
            throw new EntityNotFoundException("Componente não encontrado com o ID: " + id);
        }
        componentRepository.deleteById(id);
    }
}
