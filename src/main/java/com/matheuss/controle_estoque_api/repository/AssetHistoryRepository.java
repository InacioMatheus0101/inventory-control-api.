package com.matheuss.controle_estoque_api.repository;

import com.matheuss.controle_estoque_api.domain.history.AssetHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetHistoryRepository extends JpaRepository<AssetHistory, Long> {
    
    List<AssetHistory> findByAssetIdOrderByEventDateDesc(Long assetId);
}
