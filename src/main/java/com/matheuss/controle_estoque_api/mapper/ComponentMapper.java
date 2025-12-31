package com.matheuss.controle_estoque_api.mapper;

import com.matheuss.controle_estoque_api.domain.Component;
import com.matheuss.controle_estoque_api.domain.Computer;
import com.matheuss.controle_estoque_api.dto.ComponentCreateDTO;
import com.matheuss.controle_estoque_api.dto.ComponentResponseDTO;
import com.matheuss.controle_estoque_api.dto.ComponentUpdateDTO;
import com.matheuss.controle_estoque_api.dto.ComputerSimpleResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { ReferenceMapper.class })
public interface ComponentMapper {

    // --- MAPEAMENTO PARA ENTIDADE ---

    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "locationId", target = "location")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "computerId", target = "computer")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notes", ignore = true)
    Component toEntity(ComponentCreateDTO dto);

    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "locationId", target = "location")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "computerId", target = "computer")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notes", ignore = true)
    void updateEntityFromDto(ComponentUpdateDTO dto, @MappingTarget Component component);


    // --- MAPEAMENTO PARA DTO DE RESPOSTA ---

    ComponentResponseDTO toResponseDTO(Component component);

    // --- MAPEAMENTO AUXILIAR ---
    // Ensina o MapStruct a converter um Computer em um ComputerSimpleResponseDTO
    ComputerSimpleResponseDTO toComputerSimpleDTO(Computer computer);
}
