package com.matheuss.controle_estoque_api.service;

import com.matheuss.controle_estoque_api.domain.Computer;
import com.matheuss.controle_estoque_api.domain.history.HistoryEventType; // <-- IMPORT ADICIONADO
import com.matheuss.controle_estoque_api.dto.ComputerCreateDTO;
import com.matheuss.controle_estoque_api.dto.ComputerResponseDTO;
import com.matheuss.controle_estoque_api.dto.ComputerUpdateDTO;
import com.matheuss.controle_estoque_api.mapper.ComputerMapper;
import com.matheuss.controle_estoque_api.repository.ComputerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComputerService {

    @Autowired
    private ComputerRepository computerRepository;

    @Autowired
    private ComputerMapper computerMapper;

    // ====================================================================
    // == PASSO 1: INJETAR O SERVIÇO DE HISTÓRICO ==
    // ====================================================================
    @Autowired
    private AssetHistoryService assetHistoryService;

    @Transactional
    public ComputerResponseDTO createComputer(ComputerCreateDTO dto) {
        Computer newComputer = computerMapper.toEntity(dto);
        
        // Salva o novo computador no banco para que ele tenha um ID
        Computer savedComputer = computerRepository.save(newComputer);

        // ====================================================================
        // == PASSO 2: REGISTRAR O EVENTO DE CRIAÇÃO NO HISTÓRICO ==
        // O usuário associado é 'null' porque o ativo está apenas sendo cadastrado.
        // ====================================================================
        assetHistoryService.registerEvent(savedComputer, HistoryEventType.CRIACAO, "Ativo cadastrado no sistema.", null);

        // Retorna o DTO do computador salvo, que agora já contém o evento de criação em sua lista de histórico
        return computerMapper.toResponseDTO(savedComputer);
    }

    @Transactional(readOnly = true)
    public List<ComputerResponseDTO> getAllComputers() {
        return computerRepository.findAll().stream()
                .map(computerMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ComputerResponseDTO getComputerById(Long id) {
        return computerRepository.findById(id)
                .map(computerMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Computador não encontrado com o ID: " + id));
    }
    
    @Transactional
    public ComputerResponseDTO updateComputer(Long id, ComputerUpdateDTO updateDTO) {
        Computer existingComputer = computerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Computador não encontrado com o ID: " + id));

        computerMapper.updateEntityFromDto(updateDTO, existingComputer);

        Computer updatedComputer = computerRepository.save(existingComputer);

        return computerMapper.toResponseDTO(updatedComputer);
    }

    @Transactional
    public boolean deleteComputer(Long id) {
        if (computerRepository.existsById(id)) {
            computerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
