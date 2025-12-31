package com.matheuss.controle_estoque_api.service;

import com.matheuss.controle_estoque_api.domain.Location;
import com.matheuss.controle_estoque_api.dto.LocationCreateDTO;
import com.matheuss.controle_estoque_api.dto.LocationResponseDTO;
import com.matheuss.controle_estoque_api.dto.LocationUpdateDTO;
import com.matheuss.controle_estoque_api.mapper.LocationMapper; // IMPORT
import com.matheuss.controle_estoque_api.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationMapper locationMapper; // INJEÇÃO

    @Transactional
    public LocationResponseDTO createLocation(LocationCreateDTO dto) {
        Location newLocation = locationMapper.toEntity(dto);
        Location savedLocation = locationRepository.save(newLocation);
        return locationMapper.toResponseDTO(savedLocation);
    }

    @Transactional(readOnly = true)
    public List<LocationResponseDTO> findAllLocations() {
        return locationRepository.findAll().stream()
                .map(locationMapper::toResponseDTO) // USO DO MAPPER
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<LocationResponseDTO> findLocationById(Long id) {
        return locationRepository.findById(id)
                .map(locationMapper::toResponseDTO); // USO DO MAPPER
    }

    @Transactional
    public Optional<LocationResponseDTO> updateLocation(Long id, LocationUpdateDTO dto) {
        return locationRepository.findById(id).map(existingLocation -> {
            locationMapper.updateEntityFromDto(dto, existingLocation); // USO DO MAPPER
            Location updatedLocation = locationRepository.save(existingLocation);
            return locationMapper.toResponseDTO(updatedLocation);
        });
    }

    @Transactional
    public boolean deleteLocation(Long id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
