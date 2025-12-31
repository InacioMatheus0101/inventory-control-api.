package com.matheuss.controle_estoque_api.dto;

import com.matheuss.controle_estoque_api.domain.enums.AssetStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PeripheralCreateDTO {
    // Campos herdados de Asset
    private String assetTag;
    private AssetStatus status;
    private LocalDate purchaseDate;
    private Long supplierId;
    private Long locationId;

    // Campos específicos de Peripheral
    private String type; // Ex: "Mouse", "Teclado", "Monitor"
    private String name;
    private String model;
    private String serialNumber;

    // Relacionamento opcional com Computer
    private Long computerId;
}
