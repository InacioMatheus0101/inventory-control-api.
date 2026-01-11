package com.matheuss.controle_estoque_api.controller;

import com.matheuss.controle_estoque_api.dto.ComponentCreateDTO;
import com.matheuss.controle_estoque_api.dto.ComponentResponseDTO;
import com.matheuss.controle_estoque_api.dto.ComponentUpdateDTO;
import com.matheuss.controle_estoque_api.service.ComponentService;
import jakarta.validation.Valid; // <<< IMPORT NECESSÁRIO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/components" )
public class ComponentController {

    @Autowired
    private ComponentService componentService;

    // --- CREATE ---
    @PostMapping
    public ResponseEntity<ComponentResponseDTO> createComponent(@RequestBody @Valid ComponentCreateDTO dto) {
        ComponentResponseDTO createdComponent = componentService.createComponent(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdComponent.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdComponent);
    }

    // --- READ (ALL) ---
    @GetMapping
    public ResponseEntity<List<ComponentResponseDTO>> getAllComponents() {
        List<ComponentResponseDTO> components = componentService.getAllComponents();
        return ResponseEntity.ok(components);
    }

    // --- READ (BY ID) (Simplificado) ---
    @GetMapping("/{id}")
    public ResponseEntity<ComponentResponseDTO> getComponentById(@PathVariable Long id) {
        ComponentResponseDTO component = componentService.getComponentById(id);
        return ResponseEntity.ok(component);
    }

    // --- UPDATE (Simplificado) ---
    @PutMapping("/{id}")
    public ResponseEntity<ComponentResponseDTO> updateComponent(@PathVariable Long id, @RequestBody @Valid ComponentUpdateDTO dto) {
        ComponentResponseDTO updatedComponent = componentService.updateComponent(id, dto);
        return ResponseEntity.ok(updatedComponent);
    }

    // --- DELETE (Simplificado) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable Long id) {
        componentService.deleteComponent(id);
        return ResponseEntity.noContent().build();
    }
}
