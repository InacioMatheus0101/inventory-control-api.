package com.matheuss.controle_estoque_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matheuss.controle_estoque_api.domain.Category;
import com.matheuss.controle_estoque_api.domain.Computer;
import com.matheuss.controle_estoque_api.domain.Supplier;
import com.matheuss.controle_estoque_api.dto.ComputerCreateDTO;
import com.matheuss.controle_estoque_api.repository.CategoryRepository;
import com.matheuss.controle_estoque_api.repository.ComputerRepository;
import com.matheuss.controle_estoque_api.repository.SupplierRepository;

@Service
public class ComputerService {

    @Autowired
    private ComputerRepository computerRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Transactional
    public Computer createComputer(ComputerCreateDTO dto) {
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

        return computerRepository.save(newComputer);
    }
}
