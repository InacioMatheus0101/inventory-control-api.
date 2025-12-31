package com.matheuss.controle_estoque_api.controller;

import com.matheuss.controle_estoque_api.dto.PeripheralCreateDTO;
import com.matheuss.controle_estoque_api.dto.PeripheralResponseDTO;
import com.matheuss.controle_estoque_api.dto.PeripheralUpdateDTO;
import com.matheuss.controle_estoque_api.service.PeripheralService;
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
    public ResponseEntity<PeripheralResponseDTO> createPeripheral(@RequestBody PeripheralCreateDTO dto) {
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
        List<PeripheralResponseDTO> peripherals = peripheralService.findAllPeripherals();
        return ResponseEntity.ok(peripherals);
    }

    // --- READ (BY ID) ---
    @GetMapping("/{id}")
    public ResponseEntity<PeripheralResponseDTO> getPeripheralById(@PathVariable("id") Long id) {
        return peripheralService.findPeripheralById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- UPDATE ---
    @PutMapping("/{id}")
    public ResponseEntity<PeripheralResponseDTO> updatePeripheral(@PathVariable("id") Long id, @RequestBody PeripheralUpdateDTO dto) {
        return peripheralService.updatePeripheral(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- DELETE ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeripheral(@PathVariable("id") Long id) {
        boolean wasDeleted = peripheralService.deletePeripheral(id);
        if (wasDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
