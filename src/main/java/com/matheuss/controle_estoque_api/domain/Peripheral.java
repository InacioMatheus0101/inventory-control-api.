package com.matheuss.controle_estoque_api.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("PERIPHERAL")
@Data
@EqualsAndHashCode(callSuper = true)
public class Peripheral extends Asset {

    // Campos específicos de um periférico
    
    @Column(nullable = false)
    private String type; // Ex: "Mouse", "Teclado", "Headset", "Monitor"

    private String name; // Ex: "Mouse Gamer Logitech G502", "Teclado Mecânico HyperX"
    private String model; // Ex: "G502 Hero", "Alloy FPS Pro"
    private String serialNumber; // Número de série único do dispositivo

    // Relacionamento opcional: um periférico pode estar conectado a um computador.
    // Usamos FetchType.LAZY para otimização.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "computer_id")
    private Computer computer;
}
