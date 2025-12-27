package com.matheuss.controle_estoque_api.dto;

import java.time.LocalDate;

import com.matheuss.controle_estoque_api.domain.AssetStatus;
import com.matheuss.controle_estoque_api.domain.Category;
import com.matheuss.controle_estoque_api.domain.Supplier;

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
    private int ramSizeInGB;
    private int storageSizeInGB;
    private String os;
    private Category category;
    private Supplier supplier;
}
