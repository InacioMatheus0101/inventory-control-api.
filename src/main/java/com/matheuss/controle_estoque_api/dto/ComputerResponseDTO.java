package com.matheuss.controle_estoque_api.dto;

import com.matheuss.controle_estoque_api.domain.enums.AssetStatus;
import com.matheuss.controle_estoque_api.domain.enums.EquipmentState; 
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime; 
import java.util.List;

@Data
public class ComputerResponseDTO {

    private Long id;
    private String assetTag;
    private AssetStatus status;
    private EquipmentState equipmentState; // 3. Adicione o estado do equipamento
    private LocalDate purchaseDate;
    private String notes;

    // DTOs aninhados para relacionamentos
    private SupplierResponseDTO supplier;
    private LocationResponseDTO location;
    private CategoryResponseDTO category;
    private List<ComponentResponseDTO> components;

    // Campos específicos de Computer
    private String name;
    private String serialNumber;
    private String cpu;
    private int ramSizeInGB;
    private int storageSizeInGB;
    private String os;

    // Campos de Auditoria
    private LocalDateTime createdAt; // 4. Adicione a data de criação
    private LocalDateTime updatedAt; // 5. Adicione a data de atualização
}
