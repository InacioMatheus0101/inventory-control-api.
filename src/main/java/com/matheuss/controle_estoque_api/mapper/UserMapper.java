package com.matheuss.controle_estoque_api.mapper;

import com.matheuss.controle_estoque_api.domain.*;
import com.matheuss.controle_estoque_api.dto.AssetSimpleResponseDTO;
import com.matheuss.controle_estoque_api.dto.UserCreateDTO;
import com.matheuss.controle_estoque_api.dto.UserResponseDTO;
import com.matheuss.controle_estoque_api.dto.UserUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assets", ignore = true) // Ignoramos na conversão inicial
    User toEntity(UserCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assets", ignore = true) // Ignoramos na conversão inicial
    void updateEntityFromDto(UserUpdateDTO dto, @MappingTarget User user);

    // ====================================================================
    // == ABORDAGEM MANUAL E À PROVA DE FALHAS ==
    // Em vez de deixar o MapStruct mapear o User completo, nós o ajudamos.
    // O MapStruct mapeará os campos simples (id, name, username, department).
    // O campo 'assets' será preenchido pelo método default abaixo.
    // ====================================================================
    @Mapping(source = "user.assets", target = "assets") // Dizemos para usar o método customizado
    UserResponseDTO toResponseDTO(User user);

    // Este método default ensina o MapStruct a converter a lista de Assets
    default List<AssetSimpleResponseDTO> mapAssetsToSimpleDTOs(List<Asset> assets) {
        if (assets == null) {
            return Collections.emptyList();
        }
        return assets.stream()
                .map(asset -> {
                    AssetSimpleResponseDTO dto = new AssetSimpleResponseDTO();
                    dto.setId(asset.getId());
                    dto.setAssetTag(asset.getAssetTag());
                    dto.setStatus(asset.getStatus());

                    // Lógica manual para pegar o nome correto
                    if (asset instanceof Computer) {
                        dto.setName(((Computer) asset).getName());
                    } else if (asset instanceof Peripheral) {
                        dto.setName(((Peripheral) asset).getName());
                    } else if (asset instanceof Component) {
                        dto.setName(((Component) asset).getName());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
