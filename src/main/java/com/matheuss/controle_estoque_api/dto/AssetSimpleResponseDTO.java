package com.matheuss.controle_estoque_api.dto;

import com.matheuss.controle_estoque_api.domain.enums.AssetStatus;
import lombok.Data;

@Data
public class AssetSimpleResponseDTO {
    private Long id;
    private String assetTag;
    private AssetStatus status;
    private String name; // Nome do Computador, Periférico ou Componente
}
