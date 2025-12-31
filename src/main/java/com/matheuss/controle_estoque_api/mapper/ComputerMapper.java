package com.matheuss.controle_estoque_api.mapper;

import com.matheuss.controle_estoque_api.domain.Computer;
import com.matheuss.controle_estoque_api.dto.ComputerCreateDTO;
import com.matheuss.controle_estoque_api.dto.ComputerResponseDTO;
import com.matheuss.controle_estoque_api.dto.ComputerUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

// A mágica está aqui: adicionamos ReferenceMapper.class à propriedade 'uses'
@Mapper(componentModel = "spring", uses = {ReferenceMapper.class})
public interface ComputerMapper {

    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "locationId", target = "location")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notes", ignore = true)
    Computer toEntity(ComputerCreateDTO dto);

    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "locationId", target = "location")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notes", ignore = true)
    void updateEntityFromDto(ComputerUpdateDTO dto, @MappingTarget Computer computer);

    // Para o DTO de resposta, não precisamos do ReferenceMapper,
    // mas o MapStruct é inteligente o suficiente para não usá-lo aqui.
    ComputerResponseDTO toResponseDTO(Computer computer);
}
