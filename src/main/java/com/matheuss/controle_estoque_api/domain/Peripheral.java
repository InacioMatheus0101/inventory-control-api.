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
    
    // O campo 'type' foi movido para cá, pois é específico de Periférico.
    // A anotação @Column(nullable = true) foi a correção crucial que fizemos.
    @Column(nullable = true) 
    private String type; // Ex: "Mouse", "Teclado", "Headset", "Monitor"

    private String name; // Ex: "Mouse Gamer Logitech G502", "Teclado Mecânico HyperX"
    private String model; // Ex: "G502 Hero", "Alloy FPS Pro"
    private String serialNumber; // Número de série único do dispositivo

    // ====================================================================
    // == RELACIONAMENTO OPCIONAL COM COMPUTADOR ==
    // Um periférico pode ou não estar conectado a um computador.
    // A ausência de 'nullable = false' na @JoinColumn torna a coluna anulável por padrão.
    // ====================================================================
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "computer_id")
    private Computer computer;
}
