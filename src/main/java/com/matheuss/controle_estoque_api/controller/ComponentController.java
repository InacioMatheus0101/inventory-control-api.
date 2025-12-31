package com.matheuss.controle_estoque_api.controller;

import com.matheuss.controle_estoque_api.dto.ComponentCreateDTO;
import com.matheuss.controle_estoque_api.dto.ComponentResponseDTO;
import com.matheuss.controle_estoque_api.dto.ComponentUpdateDTO;
import com.matheuss.controle_estoque_api.service.ComponentService;
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
    public ResponseEntity<ComponentResponseDTO> createComponent(@RequestBody ComponentCreateDTO dto) {
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
        List<ComponentResponseDTO> components = componentService.findAllComponents();
        return ResponseEntity.ok(components);
    }

    // --- READ (BY ID) ---
    @GetMapping("/{id}")
    public ResponseEntity<ComponentResponseDTO> getComponentById(@PathVariable("id") Long id) {
        return componentService.findComponentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- UPDATE ---
    @PutMapping("/{id}")
    public ResponseEntity<ComponentResponseDTO> updateComponent(@PathVariable("id") Long id, @RequestBody ComponentUpdateDTO dto) {
        return componentService.updateComponent(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- DELETE ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable("id") Long id) {
        boolean wasDeleted = componentService.deleteComponent(id);
        if (wasDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
