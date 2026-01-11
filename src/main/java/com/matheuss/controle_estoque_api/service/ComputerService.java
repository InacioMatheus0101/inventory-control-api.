package com.matheuss.controle_estoque_api.service;

import com.matheuss.controle_estoque_api.domain.Computer;
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

    // As injeções de SupplierRepository, CategoryRepository, etc., não são mais necessárias aqui.

    // --- create, getAll, getById (Permanecem os mesmos) ---
    @Transactional
    public ComputerResponseDTO createComputer(ComputerCreateDTO dto) {
        Computer newComputer = computerMapper.toEntity(dto);
        Computer savedComputer = computerRepository.save(newComputer);
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
    
    // O código manual foi removido. O MapStruct agora faz todo o trabalho.

    @Transactional
    public ComputerResponseDTO updateComputer(Long id, ComputerUpdateDTO updateDTO) {
        // 1. Busca a entidade
        Computer existingComputer = computerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Computador não encontrado com o ID: " + id));

        // 2. MapStruct faz todo o trabalho de atualização
        computerMapper.updateEntityFromDto(updateDTO, existingComputer);

        // 3. Salva a entidade
        Computer updatedComputer = computerRepository.save(existingComputer);

        // 4. Retorna a resposta
        return computerMapper.toResponseDTO(updatedComputer);
    }

    // --- delete (Permanece o mesmo) ---
    @Transactional
    public boolean deleteComputer(Long id) {
        if (computerRepository.existsById(id)) {
            computerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
