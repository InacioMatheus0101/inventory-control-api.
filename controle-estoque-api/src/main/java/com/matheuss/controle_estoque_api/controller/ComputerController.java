package com.matheuss.controle_estoque_api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.matheuss.controle_estoque_api.dto.ComputerCreateDTO;
import com.matheuss.controle_estoque_api.dto.ComputerResponseDTO;
import com.matheuss.controle_estoque_api.dto.ComputerUpdateDTO;
import com.matheuss.controle_estoque_api.service.ComputerService;

@RestController
@RequestMapping("/computers" )
public class ComputerController {

    @Autowired
    private ComputerService computerService;

    // CREATE
    @PostMapping
    public ResponseEntity<ComputerResponseDTO> createComputer(@RequestBody ComputerCreateDTO dto) {
        ComputerResponseDTO newComputerDto = computerService.createComputer(dto);
        
        // Constrói a URI do novo recurso criado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newComputerDto.getId())
                .toUri();
                
        return ResponseEntity.created(location).body(newComputerDto);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<ComputerResponseDTO>> getAllComputers() {
        List<ComputerResponseDTO> computers = computerService.findAllComputers();
        return ResponseEntity.ok(computers);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ComputerResponseDTO> getComputerById(@PathVariable Long id) {
        return computerService.findComputerById(id)
                .map(dto -> ResponseEntity.ok(dto))
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ComputerResponseDTO> updateComputer(@PathVariable Long id, @RequestBody ComputerUpdateDTO dto) {
        return computerService.updateComputer(id, dto)
                .map(updatedDto -> ResponseEntity.ok(updatedDto))
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComputer(@PathVariable Long id) {
        if (computerService.deleteComputer(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
