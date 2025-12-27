package com.matheuss.controle_estoque_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matheuss.controle_estoque_api.domain.Supplier;
import com.matheuss.controle_estoque_api.dto.SupplierCreateDTO;
import com.matheuss.controle_estoque_api.repository.SupplierRepository;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    // --- CREATE ---
    @Transactional
    public Supplier createSupplier(SupplierCreateDTO dto) {
        Supplier newSupplier = new Supplier();
        newSupplier.setName(dto.getName());
        newSupplier.setCnpj(dto.getCnpj());
        newSupplier.setEmail(dto.getEmail());
        newSupplier.setPhone(dto.getPhone());
        return supplierRepository.save(newSupplier);
    }

    // --- READ ---
    @Transactional(readOnly = true)
    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Supplier> findById(Long id) {
        return supplierRepository.findById(id);
    }

    // --- DELETE ---
    @Transactional
    public boolean delete(Long id) {
        if (supplierRepository.existsById(id)) {
            supplierRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
