package com.matheuss.controle_estoque_api.dto;

import com.matheuss.controle_estoque_api.domain.enums.AssetStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ComponentUpdateDTO {
    // Campos que podem ser atualizados
    private String assetTag;
    private AssetStatus status;
    private LocalDate purchaseDate;
    private Long supplierId;
    private Long locationId;

    private String name;
    private String model;
    private String serialNumber;

    private Long categoryId;
    private Long computerId;
}
