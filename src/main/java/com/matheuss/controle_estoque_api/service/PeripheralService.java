package com.matheuss.controle_estoque_api.service;

import com.matheuss.controle_estoque_api.domain.Peripheral;
import com.matheuss.controle_estoque_api.dto.PeripheralCreateDTO;
import com.matheuss.controle_estoque_api.dto.PeripheralResponseDTO;
import com.matheuss.controle_estoque_api.dto.PeripheralUpdateDTO;
import com.matheuss.controle_estoque_api.mapper.PeripheralMapper;
import com.matheuss.controle_estoque_api.repository.PeripheralRepository;
import jakarta.persistence.EntityNotFoundException; // <<< IMPORT NECESSÁRIO
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

    // --- CREATE (Permanece o mesmo) ---
    @Transactional
    public PeripheralResponseDTO createPeripheral(PeripheralCreateDTO dto) {
        Peripheral newPeripheral = peripheralMapper.toEntity(dto);
        Peripheral savedPeripheral = peripheralRepository.save(newPeripheral);
        return peripheralMapper.toResponseDTO(savedPeripheral);
    }

    // --- READ (ALL) (Muda o nome para consistência) ---
    @Transactional(readOnly = true)
    public List<PeripheralResponseDTO> getAllPeripherals() {
        return peripheralRepository.findAll().stream()
                .map(peripheralMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // --- READ (BY ID) (Refatorado para lançar exceção) ---
    @Transactional(readOnly = true)
    public PeripheralResponseDTO getPeripheralById(Long id) {
        return peripheralRepository.findById(id)
                .map(peripheralMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Periférico não encontrado com o ID: " + id));
    }

    // --- UPDATE (Refatorado para lançar exceção e usar o mapper elegante) ---
    @Transactional
    public PeripheralResponseDTO updatePeripheral(Long id, PeripheralUpdateDTO dto) {
        Peripheral existingPeripheral = peripheralRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Periférico não encontrado com o ID: " + id));

        peripheralMapper.updateEntityFromDto(dto, existingPeripheral);
        Peripheral updatedPeripheral = peripheralRepository.save(existingPeripheral);
        return peripheralMapper.toResponseDTO(updatedPeripheral);
    }

    // --- DELETE (Refatorado para ser void e lançar exceção) ---
    @Transactional
    public void deletePeripheral(Long id) {
        if (!peripheralRepository.existsById(id)) {
            throw new EntityNotFoundException("Periférico não encontrado com o ID: " + id);
        }
        peripheralRepository.deleteById(id);
    }
}
