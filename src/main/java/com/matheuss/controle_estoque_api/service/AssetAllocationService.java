package com.matheuss.controle_estoque_api.service;

import com.matheuss.controle_estoque_api.domain.Asset;
import com.matheuss.controle_estoque_api.domain.User;
import com.matheuss.controle_estoque_api.domain.enums.AssetStatus;
import com.matheuss.controle_estoque_api.repository.AssetRepository;
import com.matheuss.controle_estoque_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssetAllocationService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void assignAsset(Long assetId, Long userId) {
        // 1. Buscar as entidades do banco de dados. Se não encontrar, lança exceção.
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new EntityNotFoundException("Ativo não encontrado com o ID: " + assetId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + userId));

        // 2. Aplicar as regras de negócio
        if (asset.getStatus() != AssetStatus.EM_ESTOQUE) {
            throw new IllegalStateException("Operação não permitida: O ativo com a etiqueta '" + asset.getAssetTag() + "' não está em estoque.");
        }

        if (asset.getUser() != null) {
            throw new IllegalStateException("Operação não permitida: O ativo já está alocado para outro usuário.");
        }

        // 3. Realizar a alocação e atualizar o status
        asset.setUser(user);
        asset.setStatus(AssetStatus.EM_USO);
        asset.setNotes("Ativo alocado para o colaborador " + user.getName() + "."); // Adiciona uma nota de auditoria

        // 4. Salvar a alteração no banco de dados
        assetRepository.save(asset);
    }

    @Transactional
    public void unassignAsset(Long assetId) {
        // 1. Buscar o ativo
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new EntityNotFoundException("Ativo não encontrado com o ID: " + assetId));

        // 2. Aplicar as regras de negócio
        if (asset.getStatus() != AssetStatus.EM_USO) {
            throw new IllegalStateException("Operação não permitida: O ativo não está em uso para ser devolvido.");
        }

        if (asset.getUser() == null) {
            throw new IllegalStateException("Operação não permitida: O ativo não está alocado a nenhum usuário.");
        }

        // 3. Realizar a desalocação e atualizar o status
        asset.setUser(null); // Remove a associação
        asset.setStatus(AssetStatus.EM_ESTOQUE);
        asset.setNotes("Ativo devolvido ao estoque.");

        // 4. Salvar a alteração
        assetRepository.save(asset);
    }
}
