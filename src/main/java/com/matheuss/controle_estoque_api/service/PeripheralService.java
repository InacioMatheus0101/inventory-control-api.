package com.matheuss.controle_estoque_api.service;

import com.matheuss.controle_estoque_api.domain.Peripheral;
import com.matheuss.controle_estoque_api.domain.history.HistoryEventType; // <-- IMPORT ADICIONADO
import com.matheuss.controle_estoque_api.dto.PeripheralCreateDTO;
import com.matheuss.controle_estoque_api.dto.PeripheralResponseDTO;
import com.matheuss.controle_estoque_api.dto.PeripheralUpdateDTO;
import com.matheuss.controle_estoque_api.mapper.PeripheralMapper;
import com.matheuss.controle_estoque_api.repository.PeripheralRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeripheralService {

    @Autowired
    private PeripheralRepository peripheralRepository;

    @Autowired
    private PeripheralMapper peripheralMapper;

    // ====================================================================
    // == PASSO 1: INJETAR O SERVIÇO DE HISTÓRICO ==
    // ====================================================================
    @Autowired
    private AssetHistoryService assetHistoryService;

    @Transactional
    public PeripheralResponseDTO createPeripheral(PeripheralCreateDTO dto) {
        Peripheral newPeripheral = peripheralMapper.toEntity(dto);
        
        // Salva o novo periférico no banco para que ele tenha um ID
        Peripheral savedPeripheral = peripheralRepository.save(newPeripheral);

        // ====================================================================
        // == PASSO 2: REGISTRAR O EVENTO DE CRIAÇÃO NO HISTÓRICO ==
        // ====================================================================
        assetHistoryService.registerEvent(savedPeripheral, HistoryEventType.CRIACAO, "Ativo cadastrado no sistema.", null);

        return peripheralMapper.toResponseDTO(savedPeripheral);
    }

    @Transactional(readOnly = true)
    public List<PeripheralResponseDTO> getAllPeripherals() {
        return peripheralRepository.findAll().stream()
                .map(peripheralMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PeripheralResponseDTO getPeripheralById(Long id) {
        return peripheralRepository.findById(id)
                .map(peripheralMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Periférico não encontrado com o ID: " + id));
    }

    @Transactional
    public PeripheralResponseDTO updatePeripheral(Long id, PeripheralUpdateDTO dto) {
        Peripheral existingPeripheral = peripheralRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Periférico não encontrado com o ID: " + id));

        peripheralMapper.updateEntityFromDto(dto, existingPeripheral);
        Peripheral updatedPeripheral = peripheralRepository.save(existingPeripheral);
        return peripheralMapper.toResponseDTO(updatedPeripheral);
    }

    @Transactional
    public void deletePeripheral(Long id) {
        if (!peripheralRepository.existsById(id)) {
            throw new EntityNotFoundException("Periférico não encontrado com o ID: " + id);
        }
        peripheralRepository.deleteById(id);
    }
}
