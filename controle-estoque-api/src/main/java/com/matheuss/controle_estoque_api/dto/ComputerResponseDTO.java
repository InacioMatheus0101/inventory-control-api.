package com.matheuss.controle_estoque_api.dto;

import java.time.LocalDate;

import com.matheuss.controle_estoque_api.domain.AssetStatus;

import lombok.Data;

@Data
public class ComputerResponseDTO {
    private Long id;
    private String assetTag;
    private LocalDate purchaseDate;
    private AssetStatus status;
    private String notes;
    private String name;
    private String serialNumber;
    private String cpu;
    private Integer ramSizeInGB;
    private Integer storageSizeInGB;
    private String os;

    // AQUI ESTÁ A MUDANÇA CRUCIAL:
    // Usamos os DTOs simples para evitar problemas de serialização e referências circulares.
    private CategorySimpleResponseDTO category;
    private SupplierSimpleResponseDTO supplier;
}
