package com.matheuss.controle_estoque_api.mapper;

import com.matheuss.controle_estoque_api.domain.Computer;
import com.matheuss.controle_estoque_api.domain.User;
import com.matheuss.controle_estoque_api.dto.ComputerCreateDTO;
import com.matheuss.controle_estoque_api.dto.ComputerResponseDTO;
import com.matheuss.controle_estoque_api.dto.ComputerUpdateDTO;
import com.matheuss.controle_estoque_api.dto.UserSimpleResponseDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    uses = { ReferenceMapper.class, ComponentMapper.class },
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ComputerMapper {

    // --- MAPEAMENTO DE CRIAÇÃO (Permanece o mesmo) ---
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "locationId", target = "location")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "components", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Computer toEntity(ComputerCreateDTO dto);

    // Reativamos o mapeamento das entidades. O MapStruct usará o ReferenceMapper
  
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "locationId", target = "location")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "components", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDto(ComputerUpdateDTO dto, @MappingTarget Computer computer);

    // --- MAPEAMENTO DE RESPOSTA (Permanece o mesmo) ---
    @Mapping(source = "components", target = "components")
    ComputerResponseDTO toResponseDTO(Computer computer);

    
}
