package com.matheuss.controle_estoque_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matheuss.controle_estoque_api.domain.Supplier;
import com.matheuss.controle_estoque_api.repository.SupplierRepository;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Optional<Supplier> findById(Long id) {
        return supplierRepository.findById(id);
    }

    public Supplier update(Long id, Supplier supplierToUpdate) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);

        if (optionalSupplier.isPresent()) {
            Supplier existingSupplier = optionalSupplier.get();
            existingSupplier.setName(supplierToUpdate.getName());
            existingSupplier.setCnpj(supplierToUpdate.getCnpj());
            existingSupplier.setEmail(supplierToUpdate.getEmail());
            existingSupplier.setPhone(supplierToUpdate.getPhone());
            return supplierRepository.save(existingSupplier);
        } else {
            return null;
        }
    }
}
