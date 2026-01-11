package com.matheuss.controle_estoque_api.mapper;

import com.matheuss.controle_estoque_api.domain.Category;
import com.matheuss.controle_estoque_api.domain.Computer; // <-- IMPORT NOVO
import com.matheuss.controle_estoque_api.domain.Location;
import com.matheuss.controle_estoque_api.domain.Supplier;
import com.matheuss.controle_estoque_api.repository.CategoryRepository;
import com.matheuss.controle_estoque_api.repository.ComputerRepository; // <-- IMPORT NOVO
import com.matheuss.controle_estoque_api.repository.LocationRepository;
import com.matheuss.controle_estoque_api.repository.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReferenceMapper {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ComputerRepository computerRepository; // <-- INJEÇÃO NOVA

    public Category toCategory(Long categoryId) {
        if (categoryId == null) return null;
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com o ID: " + categoryId));
    }

    public Supplier toSupplier(Long supplierId) {
        if (supplierId == null) return null;
        return supplierRepository.findById(supplierId)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com o ID: " + supplierId));
    }

    public Location toLocation(Long locationId) {
        if (locationId == null) return null;
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Localização não encontrada com o ID: " + locationId));
    }

    // --- MÉTODO NOVO ADICIONADO ---
    public Computer toComputer(Long computerId) {
        // O relacionamento com Computer é opcional, então se o ID for nulo, retornamos nulo.
        if (computerId == null || computerId == 0L) {
            return null;
        }
        return computerRepository.findById(computerId)
                .orElseThrow(() -> new EntityNotFoundException("Computador não encontrado com o ID: " + computerId));
    }
}
