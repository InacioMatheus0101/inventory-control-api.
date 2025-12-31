package com.matheuss.controle_estoque_api.service;

import com.matheuss.controle_estoque_api.domain.Computer;
import com.matheuss.controle_estoque_api.dto.ComputerCreateDTO;
import com.matheuss.controle_estoque_api.dto.ComputerResponseDTO;
import com.matheuss.controle_estoque_api.dto.ComputerUpdateDTO;
import com.matheuss.controle_estoque_api.mapper.ComputerMapper;
import com.matheuss.controle_estoque_api.repository.ComputerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComputerService {

    @Autowired
    private ComputerRepository computerRepository;

    @Autowired
    private ComputerMapper computerMapper; // A única dependência de mapper necessária!

    @Transactional
    public ComputerResponseDTO createComputer(ComputerCreateDTO dto) {
        Computer newComputer = computerMapper.toEntity(dto);
        Computer savedComputer = computerRepository.save(newComputer);
        return computerMapper.toResponseDTO(savedComputer);
    }

    @Transactional(readOnly = true)
    public List<ComputerResponseDTO> findAllComputers() {
        return computerRepository.findAllWithDetails().stream()
                .map(computerMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ComputerResponseDTO> findComputerById(Long id) {
        return computerRepository.findByIdWithDetails(id)
                .map(computerMapper::toResponseDTO);
    }

    @Transactional
    public Optional<ComputerResponseDTO> updateComputer(Long id, ComputerUpdateDTO dto) {
        return computerRepository.findById(id).map(existingComputer -> {
            computerMapper.updateEntityFromDto(dto, existingComputer);
            Computer updatedComputer = computerRepository.save(existingComputer);
            return computerMapper.toResponseDTO(updatedComputer);
        });
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
