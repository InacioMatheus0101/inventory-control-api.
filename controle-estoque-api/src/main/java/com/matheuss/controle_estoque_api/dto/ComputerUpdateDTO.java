package com.matheuss.controle_estoque_api.dto;

import java.time.LocalDate;

import com.matheuss.controle_estoque_api.domain.AssetStatus;

import lombok.Data;

@Data
public class ComputerUpdateDTO {
    // Apenas os campos que permitimos que sejam atualizados
    private String name;
    private String serialNumber;
    private String cpu;
    private Integer ramSizeInGB;
    private Integer storageSizeInGB;
    private String os;
    private Long categoryId;
    private String assetTag;
    private LocalDate purchaseDate;
    private AssetStatus status;
    private String notes;
    private Long supplierId;
}
