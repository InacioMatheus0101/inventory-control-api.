package com.matheuss.controle_estoque_api.controller;

import com.matheuss.controle_estoque_api.dto.PeripheralCreateDTO;
import com.matheuss.controle_estoque_api.dto.PeripheralResponseDTO;
import com.matheuss.controle_estoque_api.dto.PeripheralUpdateDTO;
import com.matheuss.controle_estoque_api.service.PeripheralService;
import jakarta.validation.Valid; // <<< IMPORT NECESSÁRIO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/peripherals" )
public class PeripheralController {

    @Autowired
    private PeripheralService peripheralService;

    // --- CREATE ---
    @PostMapping
    public ResponseEntity<PeripheralResponseDTO> createPeripheral(@RequestBody @Valid PeripheralCreateDTO dto) {
        PeripheralResponseDTO createdPeripheral = peripheralService.createPeripheral(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPeripheral.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdPeripheral);
    }

    // --- READ (ALL) ---
    @GetMapping
    public ResponseEntity<List<PeripheralResponseDTO>> getAllPeripherals() {
        List<PeripheralResponseDTO> peripherals = peripheralService.getAllPeripherals();
        return ResponseEntity.ok(peripherals);
    }

    // --- READ (BY ID) (Simplificado) ---
    @GetMapping("/{id}")
    public ResponseEntity<PeripheralResponseDTO> getPeripheralById(@PathVariable Long id) {
        PeripheralResponseDTO peripheral = peripheralService.getPeripheralById(id);
        return ResponseEntity.ok(peripheral);
    }

    // --- UPDATE (Simplificado) ---
    @PutMapping("/{id}")
    public ResponseEntity<PeripheralResponseDTO> updatePeripheral(@PathVariable Long id, @RequestBody @Valid PeripheralUpdateDTO dto) {
        PeripheralResponseDTO updatedPeripheral = peripheralService.updatePeripheral(id, dto);
        return ResponseEntity.ok(updatedPeripheral);
    }

    // --- DELETE (Simplificado) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeripheral(@PathVariable Long id) {
        peripheralService.deletePeripheral(id);
        return ResponseEntity.noContent().build();
    }
}
