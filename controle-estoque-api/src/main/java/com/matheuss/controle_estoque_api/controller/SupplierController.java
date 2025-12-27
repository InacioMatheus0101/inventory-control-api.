package com.matheuss.controle_estoque_api.controller;

import com.matheuss.controle_estoque_api.domain.Supplier;
import com.matheuss.controle_estoque_api.dto.SupplierCreateDTO; // Importe o novo DTO
import com.matheuss.controle_estoque_api.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/suppliers" )
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    // --- CREATE ---
    // ESTE É O MÉTODO CORRIGIDO
    @PostMapping
    public ResponseEntity<Supplier> createSupplier(@RequestBody SupplierCreateDTO dto) {
        // 1. O parâmetro agora é o DTO de criação.
        // 2. Chamamos o novo método do serviço.
        Supplier newSupplier = supplierService.createSupplier(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newSupplier.getId())
                .toUri();
        return ResponseEntity.created(location).body(newSupplier);
    }

    // --- READ ---
    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        List<Supplier> suppliers = supplierService.findAll();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        Optional<Supplier> supplier = supplierService.findById(id);
        return supplier.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    // --- UPDATE ---
    // ATENÇÃO: O método de update também precisará ser refatorado para usar um DTO,
    // mas vamos focar em fazer o CREATE funcionar primeiro.

    // --- DELETE ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        boolean wasDeleted = supplierService.delete(id);
        if (wasDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
