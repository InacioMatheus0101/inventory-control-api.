package com.matheuss.controle_estoque_api.dto;

import com.matheuss.controle_estoque_api.domain.enums.AssetStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ComponentCreateDTO {
    // Campos herdados de Asset
    private String assetTag;
    private AssetStatus status;
    private LocalDate purchaseDate;
    private Long supplierId;
    private Long locationId; // Onde o componente está em estoque

    // Campos específicos de Component
    private String name; // Ex: "Corsair Vengeance LPX"
    private String model;
    private String serialNumber;

    // Relacionamentos
    private Long categoryId; // Categoria do componente (RAM, SSD, etc.)
    private Long computerId; // Opcional: ID do computador onde será instalado imediatamente
}
