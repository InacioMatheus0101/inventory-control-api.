package com.matheuss.controle_estoque_api.mapper;

import com.matheuss.controle_estoque_api.domain.Computer;
import com.matheuss.controle_estoque_api.domain.Peripheral;
import com.matheuss.controle_estoque_api.dto.ComputerSimpleResponseDTO;
import com.matheuss.controle_estoque_api.dto.PeripheralCreateDTO;
import com.matheuss.controle_estoque_api.dto.PeripheralResponseDTO;
import com.matheuss.controle_estoque_api.dto.PeripheralUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { ReferenceMapper.class }) // Usa nosso helper para buscar entidades por ID
public interface PeripheralMapper {

    // --- MAPEAMENTO PARA ENTIDADE ---

    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "locationId", target = "location")
    @Mapping(source = "computerId", target = "computer") // Mapeia o ID do computador para a entidade Computer
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notes", ignore = true)
    Peripheral toEntity(PeripheralCreateDTO dto);

    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "locationId", target = "location")
    @Mapping(source = "computerId", target = "computer")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notes", ignore = true)
    void updateEntityFromDto(PeripheralUpdateDTO dto, @MappingTarget Peripheral peripheral);


    // --- MAPEAMENTO PARA DTO DE RESPOSTA ---

    PeripheralResponseDTO toResponseDTO(Peripheral peripheral);

    // --- MAPEAMENTO AUXILIAR ---
    // O MapStruct precisa saber como converter um Computer completo
    // para o ComputerSimpleResponseDTO que usamos no PeripheralResponseDTO.
    ComputerSimpleResponseDTO toComputerSimpleDTO(Computer computer);
}
