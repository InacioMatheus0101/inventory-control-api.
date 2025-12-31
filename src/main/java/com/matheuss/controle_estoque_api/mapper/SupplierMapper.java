package com.matheuss.controle_estoque_api.mapper;

import com.matheuss.controle_estoque_api.domain.Supplier;
import com.matheuss.controle_estoque_api.dto.SupplierCreateDTO;
import com.matheuss.controle_estoque_api.dto.SupplierResponseDTO;
import com.matheuss.controle_estoque_api.dto.SupplierUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    // Converte Entidade para DTO de Resposta
    SupplierResponseDTO toResponseDTO(Supplier supplier);

    // Converte DTO de Criação para Entidade
    @Mapping(target = "id", ignore = true)
    Supplier toEntity(SupplierCreateDTO dto);

    // Atualiza uma entidade existente a partir de um DTO de atualização
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(SupplierUpdateDTO dto, @MappingTarget Supplier supplier);
}
