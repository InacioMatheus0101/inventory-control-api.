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
@DiscriminatorValue("COMPONENT")
@Data
@EqualsAndHashCode(callSuper = true)
public class Component extends Asset {

    // --- CAMPOS DO SEU CÓDIGO, MANTIDOS E REFINADOS ---

    // O tipo de componente (RAM, SSD, etc.) virá da Categoria.
    // O 'name' pode ser o nome do produto.
    @Column(name = "component_name")
    private String name; // Ex: "Corsair Vengeance LPX", "Kingston A400"

    private String model; // Adicionamos um campo para o modelo específico
    private String serialNumber; // Essencial para rastrear peças individuais

    // --- RELACIONAMENTOS ---

    // Sua ideia de usar Categoria é excelente. Mantemos.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // Adicionamos o relacionamento OPCIONAL com Computer, como discutimos.
    // Se 'computer_id' for nulo, o componente está em estoque.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "computer_id")
    private Computer computer;
}
