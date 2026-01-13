package com.matheuss.controle_estoque_api.controller;

import com.matheuss.controle_estoque_api.service.AssetAllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assets" )
public class AssetAllocationController {

    @Autowired
    private AssetAllocationService assetAllocationService;

    // Endpoint para ALOCAR um ativo a um usuário
    // Ex: PATCH http://localhost:8080/api/assets/1/assign/1
    @PatchMapping("/{assetId}/assign/{userId}" )
    public ResponseEntity<Void> assignAsset(@PathVariable Long assetId, @PathVariable Long userId) {
        assetAllocationService.assignAsset(assetId, userId);
        return ResponseEntity.ok().build(); // Retorna 200 OK se a operação for bem-sucedida
    }

    // Endpoint para DESALOCAR um ativo (retornar ao estoque)
    // Ex: PATCH http://localhost:8080/api/assets/1/unassign
    @PatchMapping("/{assetId}/unassign" )
    public ResponseEntity<Void> unassignAsset(@PathVariable Long assetId) {
        assetAllocationService.unassignAsset(assetId);
        return ResponseEntity.ok().build(); // Retorna 200 OK
    }
}
