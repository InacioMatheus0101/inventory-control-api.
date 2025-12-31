package com.matheuss.controle_estoque_api.mapper;

import com.matheuss.controle_estoque_api.domain.Category;
import com.matheuss.controle_estoque_api.dto.CategoryCreateDTO;
import com.matheuss.controle_estoque_api.dto.CategoryResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    // Converte Entidade para DTO de Resposta
    CategoryResponseDTO toResponseDTO(Category category);

    // Converte DTO de Criação para Entidade
    @Mapping(target = "id", ignore = true) // <-- ADICIONE ESTA LINHA
    Category toEntity(CategoryCreateDTO dto);
}
