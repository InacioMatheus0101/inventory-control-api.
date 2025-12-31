package com.matheuss.controle_estoque_api.mapper;

import com.matheuss.controle_estoque_api.domain.Location;
import com.matheuss.controle_estoque_api.dto.LocationCreateDTO;
import com.matheuss.controle_estoque_api.dto.LocationResponseDTO;
import com.matheuss.controle_estoque_api.dto.LocationUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationResponseDTO toResponseDTO(Location location);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assets", ignore = true) // Ignora a lista de ativos
    Location toEntity(LocationCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assets", ignore = true)
    void updateEntityFromDto(LocationUpdateDTO dto, @MappingTarget Location location);
}
