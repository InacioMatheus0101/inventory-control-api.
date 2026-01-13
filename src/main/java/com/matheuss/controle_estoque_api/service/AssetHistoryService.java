package com.matheuss.controle_estoque_api.service;

import com.matheuss.controle_estoque_api.domain.Asset;
import com.matheuss.controle_estoque_api.domain.User;
import com.matheuss.controle_estoque_api.domain.history.AssetHistory;
import com.matheuss.controle_estoque_api.domain.history.HistoryEventType;
import com.matheuss.controle_estoque_api.repository.AssetHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssetHistoryService {

    @Autowired
    private AssetHistoryRepository assetHistoryRepository;

    @Transactional
    public void registerEvent(Asset asset, HistoryEventType eventType, String details, User associatedUser) {
        AssetHistory historyRecord = new AssetHistory(asset, eventType, details, associatedUser);
        assetHistoryRepository.save(historyRecord);
    }
}
