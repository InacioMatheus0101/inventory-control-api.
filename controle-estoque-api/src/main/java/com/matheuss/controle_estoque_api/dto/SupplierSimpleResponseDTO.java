package com.matheuss.controle_estoque_api.dto;

import lombok.Data;

@Data
public class SupplierSimpleResponseDTO {
    private Long id;
    private String name;
    private String email;
}
