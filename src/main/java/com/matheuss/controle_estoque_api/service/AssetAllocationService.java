package com.matheuss.controle_estoque_api.service;

import com.matheuss.controle_estoque_api.domain.Asset;
import com.matheuss.controle_estoque_api.domain.User;
import com.matheuss.controle_estoque_api.domain.enums.AssetStatus;
import com.matheuss.controle_estoque_api.domain.history.HistoryEventType; // <-- IMPORT ADICIONADO
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

    // ====================================================================
    // == NOVA INJEÇÃO: SERVIÇO PARA REGISTRAR O HISTÓRICO ==
    // ====================================================================
    @Autowired
    private AssetHistoryService assetHistoryService;

    @Transactional
    public void assignAsset(Long assetId, Long userId) {
        // 1. Buscar as entidades do banco de dados.
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new EntityNotFoundException("Ativo não encontrado com o ID: " + assetId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID: " + userId));

        // 2. Aplicar as regras de negócio (sua lógica original mantida)
        if (asset.getStatus() != AssetStatus.EM_ESTOQUE) {
            throw new IllegalStateException("Operação não permitida: O ativo com a etiqueta '" + asset.getAssetTag() + "' não está em estoque.");
        }

        if (asset.getUser() != null) {
            throw new IllegalStateException("Operação não permitida: O ativo já está alocado para outro usuário.");
        }

        // 3. Realizar a alocação e atualizar o status
        asset.setUser(user);
        asset.setStatus(AssetStatus.EM_USO);
        // asset.setNotes("Ativo alocado para o colaborador " + user.getName() + "."); // <-- LÓGICA ANTIGA REMOVIDA

        // ====================================================================
        // == NOVA LÓGICA: REGISTRA O EVENTO DE ALOCAÇÃO NO HISTÓRICO ==
        // ====================================================================
        String details = "Ativo alocado para o colaborador: " + user.getName() + " (ID: " + user.getId() + ").";
        assetHistoryService.registerEvent(asset, HistoryEventType.ALOCACAO, details, user);

        // 4. Salvar a alteração no banco de dados (o save é redundante aqui por causa do @Transactional, mas podemos manter por clareza)
        assetRepository.save(asset);
    }

    @Transactional
    public void unassignAsset(Long assetId) {
        // 1. Buscar o ativo
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new EntityNotFoundException("Ativo não encontrado com o ID: " + assetId));

        // 2. Aplicar as regras de negócio (sua lógica original mantida)
        if (asset.getStatus() != AssetStatus.EM_USO) {
            throw new IllegalStateException("Operação não permitida: O ativo não está em uso para ser devolvido.");
        }

        if (asset.getUser() == null) {
            throw new IllegalStateException("Operação não permitida: O ativo não está alocado a nenhum usuário.");
        }

        // Captura o usuário antes de remover a associação para registrar no histórico
        User previousUser = asset.getUser();

        // 3. Realizar a desalocação e atualizar o status
        asset.setUser(null);
        asset.setStatus(AssetStatus.EM_ESTOQUE);
        // asset.setNotes("Ativo devolvido ao estoque."); // <-- LÓGICA ANTIGA REMOVIDA

        // ====================================================================
        // == NOVA LÓGICA: REGISTRA O EVENTO DE DEVOLUÇÃO NO HISTÓRICO ==
        // ====================================================================
        String details = "Ativo devolvido ao estoque. Estava anteriormente com o colaborador: " + previousUser.getName() + " (ID: " + previousUser.getId() + ").";
        assetHistoryService.registerEvent(asset, HistoryEventType.DEVOLUCAO, details, previousUser);

        // 4. Salvar a alteração
        assetRepository.save(asset);
    }
}
