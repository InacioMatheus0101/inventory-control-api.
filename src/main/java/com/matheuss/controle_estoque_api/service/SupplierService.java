package com.matheuss.controle_estoque_api.service;

import com.matheuss.controle_estoque_api.domain.Supplier;
import com.matheuss.controle_estoque_api.dto.SupplierCreateDTO;
import com.matheuss.controle_estoque_api.dto.SupplierResponseDTO;
import com.matheuss.controle_estoque_api.dto.SupplierUpdateDTO;
import com.matheuss.controle_estoque_api.mapper.SupplierMapper; // IMPORT
import com.matheuss.controle_estoque_api.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper supplierMapper; // INJEÇÃO

    @Transactional
    public SupplierResponseDTO createSupplier(SupplierCreateDTO dto) {
        Supplier newSupplier = supplierMapper.toEntity(dto);
        Supplier savedSupplier = supplierRepository.save(newSupplier);
        return supplierMapper.toResponseDTO(savedSupplier);
    }

    @Transactional(readOnly = true)
    public List<SupplierResponseDTO> findAll() {
        return supplierRepository.findAll().stream()
                .map(supplierMapper::toResponseDTO) // USO DO MAPPER
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<SupplierResponseDTO> findById(Long id) {
        return supplierRepository.findById(id)
                .map(supplierMapper::toResponseDTO); // USO DO MAPPER
    }

    @Transactional
    public Optional<SupplierResponseDTO> updateSupplier(Long id, SupplierUpdateDTO dto) {
        return supplierRepository.findById(id).map(existingSupplier -> {
            supplierMapper.updateEntityFromDto(dto, existingSupplier); // USO DO MAPPER
            Supplier updatedSupplier = supplierRepository.save(existingSupplier);
            return supplierMapper.toResponseDTO(updatedSupplier);
        });
    }

    @Transactional
    public boolean delete(Long id) {
        if (supplierRepository.existsById(id)) {
            supplierRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
