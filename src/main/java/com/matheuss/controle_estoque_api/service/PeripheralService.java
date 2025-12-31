package com.matheuss.controle_estoque_api.service;

import com.matheuss.controle_estoque_api.domain.Peripheral;
import com.matheuss.controle_estoque_api.dto.PeripheralCreateDTO;
import com.matheuss.controle_estoque_api.dto.PeripheralResponseDTO;
import com.matheuss.controle_estoque_api.dto.PeripheralUpdateDTO;
import com.matheuss.controle_estoque_api.mapper.PeripheralMapper;
import com.matheuss.controle_estoque_api.repository.PeripheralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PeripheralService {

    @Autowired
    private PeripheralRepository peripheralRepository;

    @Autowired
    private PeripheralMapper peripheralMapper;

    @Transactional
    public PeripheralResponseDTO createPeripheral(PeripheralCreateDTO dto) {
        Peripheral newPeripheral = peripheralMapper.toEntity(dto);
        Peripheral savedPeripheral = peripheralRepository.save(newPeripheral);
        return peripheralMapper.toResponseDTO(savedPeripheral);
    }

    @Transactional(readOnly = true)
    public List<PeripheralResponseDTO> findAllPeripherals() {
        return peripheralRepository.findAll().stream()
                .map(peripheralMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<PeripheralResponseDTO> findPeripheralById(Long id) {
        return peripheralRepository.findById(id)
                .map(peripheralMapper::toResponseDTO);
    }

    @Transactional
    public Optional<PeripheralResponseDTO> updatePeripheral(Long id, PeripheralUpdateDTO dto) {
        return peripheralRepository.findById(id).map(existingPeripheral -> {
            peripheralMapper.updateEntityFromDto(dto, existingPeripheral);
            Peripheral updatedPeripheral = peripheralRepository.save(existingPeripheral);
            return peripheralMapper.toResponseDTO(updatedPeripheral);
        });
    }

    @Transactional
    public boolean deletePeripheral(Long id) {
        if (peripheralRepository.existsById(id)) {
            peripheralRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
