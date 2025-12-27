package com.matheuss.controle_estoque_api.service;

// ... (mantenha todos os imports)
import com.matheuss.controle_estoque_api.domain.Category;
import com.matheuss.controle_estoque_api.domain.Computer;
import com.matheuss.controle_estoque_api.domain.Supplier;
import com.matheuss.controle_estoque_api.dto.*;
import com.matheuss.controle_estoque_api.repository.CategoryRepository;
import com.matheuss.controle_estoque_api.repository.ComputerRepository;
import com.matheuss.controle_estoque_api.repository.SupplierRepository;
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
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    // ... (o método createComputer continua igual)
    @Transactional
    public ComputerResponseDTO createComputer(ComputerCreateDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com o ID: " + dto.getCategoryId()));

        Supplier supplier = null;
        if (dto.getSupplierId() != null) {
            supplier = supplierRepository.findById(dto.getSupplierId())
                    .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado com o ID: " + dto.getSupplierId()));
        }

        Computer newComputer = new Computer();
        
        newComputer.setAssetTag(dto.getAssetTag());
        newComputer.setPurchaseDate(dto.getPurchaseDate());
        newComputer.setStatus(dto.getStatus());
        newComputer.setNotes(dto.getNotes());
        newComputer.setSupplier(supplier);
        newComputer.setName(dto.getName());
        newComputer.setSerialNumber(dto.getSerialNumber());
        newComputer.setCpu(dto.getCpu());
        newComputer.setRamSizeInGB(dto.getRamSizeInGB());
        newComputer.setStorageSizeInGB(dto.getStorageSizeInGB());
        newComputer.setOs(dto.getOs());
        newComputer.setCategory(category);

        Computer savedComputer = computerRepository.save(newComputer);
        return toResponseDTO(savedComputer);
    }


    // --- READ ---
    @Transactional(readOnly = true)
    public List<ComputerResponseDTO> findAllComputers() {
        // Agora este método já recebe os objetos completos do banco
        return computerRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ComputerResponseDTO> findComputerById(Long id) {
        // Usamos nosso novo método customizado
        return computerRepository.findByIdWithDetails(id)
                .map(this::toResponseDTO);
    }

    // ... (os métodos update e delete continuam iguais)
    @Transactional
    public Optional<ComputerResponseDTO> updateComputer(Long id, ComputerUpdateDTO dto) {
        // O findById aqui dentro da transação ainda funciona, mas para consistência poderíamos usar o findByIdWithDetails também.
        // Vamos manter assim por enquanto, pois o contexto transacional garante o lazy loading.
        return computerRepository.findById(id).map(existingComputer -> {
            if (dto.getName() != null) existingComputer.setName(dto.getName());
            if (dto.getSerialNumber() != null) existingComputer.setSerialNumber(dto.getSerialNumber());
            if (dto.getCpu() != null) existingComputer.setCpu(dto.getCpu());
            if (dto.getRamSizeInGB() != null) existingComputer.setRamSizeInGB(dto.getRamSizeInGB());
            if (dto.getStorageSizeInGB() != null) existingComputer.setStorageSizeInGB(dto.getStorageSizeInGB());
            if (dto.getOs() != null) existingComputer.setOs(dto.getOs());
            if (dto.getAssetTag() != null) existingComputer.setAssetTag(dto.getAssetTag());
            if (dto.getPurchaseDate() != null) existingComputer.setPurchaseDate(dto.getPurchaseDate());
            if (dto.getStatus() != null) existingComputer.setStatus(dto.getStatus());
            if (dto.getNotes() != null) existingComputer.setNotes(dto.getNotes());

            if (dto.getCategoryId() != null) {
                Category category = categoryRepository.findById(dto.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Categoria não encontrada para atualização."));
                existingComputer.setCategory(category);
            }
            if (dto.getSupplierId() != null) {
                Supplier supplier = supplierRepository.findById(dto.getSupplierId())
                        .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado para atualização."));
                existingComputer.setSupplier(supplier);
            }

            Computer updatedComputer = computerRepository.save(existingComputer);
            return toResponseDTO(updatedComputer);
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


    // --- MAPPER ---
    // O mapper não precisa mudar, pois ele já está correto.
    private ComputerResponseDTO toResponseDTO(Computer computer) {
        if (computer == null) {
            return null;
        }

        CategorySimpleResponseDTO categoryDto = null;
        if (computer.getCategory() != null) {
            categoryDto = new CategorySimpleResponseDTO();
            categoryDto.setId(computer.getCategory().getId());
            categoryDto.setName(computer.getCategory().getName());
        }

        SupplierSimpleResponseDTO supplierDto = null;
        if (computer.getSupplier() != null) {
            supplierDto = new SupplierSimpleResponseDTO();
            supplierDto.setId(computer.getSupplier().getId());
            supplierDto.setName(computer.getSupplier().getName());
            supplierDto.setEmail(computer.getSupplier().getEmail());
        }

        ComputerResponseDTO computerDto = new ComputerResponseDTO();
        computerDto.setId(computer.getId());
        computerDto.setAssetTag(computer.getAssetTag());
        computerDto.setPurchaseDate(computer.getPurchaseDate());
        computerDto.setStatus(computer.getStatus());
        computerDto.setNotes(computer.getNotes());
        computerDto.setName(computer.getName());
        computerDto.setSerialNumber(computer.getSerialNumber());
        computerDto.setCpu(computer.getCpu());
        computerDto.setRamSizeInGB(computer.getRamSizeInGB());
        computerDto.setStorageSizeInGB(computer.getStorageSizeInGB());
        computerDto.setOs(computer.getOs());
        
        computerDto.setCategory(categoryDto);
        computerDto.setSupplier(supplierDto);

        return computerDto;
    }
}
