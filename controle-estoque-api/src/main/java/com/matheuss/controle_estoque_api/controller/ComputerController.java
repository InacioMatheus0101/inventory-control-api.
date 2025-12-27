package com.matheuss.controle_estoque_api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheuss.controle_estoque_api.domain.Computer;
import com.matheuss.controle_estoque_api.dto.ComputerCreateDTO;
import com.matheuss.controle_estoque_api.service.ComputerService;

@RestController
@RequestMapping("/computers" )
public class ComputerController {

    @Autowired
    private ComputerService computerService;

    @PostMapping
    public ResponseEntity<Computer> createComputer(@RequestBody ComputerCreateDTO dto) {
        Computer newComputer = computerService.createComputer(dto);
        URI location = URI.create("/computers/" + newComputer.getId());
        return ResponseEntity.created(location).body(newComputer);
    }
}
