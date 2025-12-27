package com.matheuss.controle_estoque_api.dto;

import java.time.LocalDate;

import com.matheuss.controle_estoque_api.domain.AssetStatus;

import lombok.Data;

@Data
public class ComputerCreateDTO {
    private String name;
    private String serialNumber;
    private String cpu;
    private int ramSizeInGB;
    private int storageSizeInGB;
    private String os;
    private Long categoryId;
    private String assetTag;
    private LocalDate purchaseDate;
    private AssetStatus status;
    private String notes;
    private Long supplierId;
}
