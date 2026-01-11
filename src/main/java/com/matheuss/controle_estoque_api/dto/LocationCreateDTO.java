package com.matheuss.controle_estoque_api.dto;

import jakarta.validation.constraints.NotBlank; // Importe a anotação
import lombok.Data;

@Data
public class LocationCreateDTO {

    @NotBlank(message = "O número da PA (paNumber) não pode ser vazio.")
    private String paNumber;

    @NotBlank(message = "O andar (floor) não pode ser vazio.")
    private String floor;

    private String sector;
    private String description;
}
