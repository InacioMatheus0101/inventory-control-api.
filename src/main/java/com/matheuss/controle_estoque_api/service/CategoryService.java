package com.matheuss.controle_estoque_api.service;

import com.matheuss.controle_estoque_api.domain.Category;
import com.matheuss.controle_estoque_api.dto.CategoryCreateDTO;
import com.matheuss.controle_estoque_api.dto.CategoryResponseDTO;
import com.matheuss.controle_estoque_api.dto.CategoryUpdateDTO;
import com.matheuss.controle_estoque_api.mapper.CategoryMapper; // <-- IMPORT NOVO
import com.matheuss.controle_estoque_api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper; // <-- INJEÇÃO DO MAPPER

    // CREATE
    @Transactional
    public CategoryResponseDTO createCategory(CategoryCreateDTO dto) {
        Category newCategory = categoryMapper.toEntity(dto); // <-- USA O MAPPER
        Category savedCategory = categoryRepository.save(newCategory);
        return categoryMapper.toResponseDTO(savedCategory); // <-- USA O MAPPER
    }

    // READ (ALL)
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponseDTO) // <-- USA O MAPPER
                .collect(Collectors.toList());
    }

    // READ (BY ID)
    @Transactional(readOnly = true)
    public Optional<CategoryResponseDTO> findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toResponseDTO); // <-- USA O MAPPER
    }

    // UPDATE
    @Transactional
    public Optional<CategoryResponseDTO> updateCategory(Long id, CategoryUpdateDTO dto) {
        return categoryRepository.findById(id).map(existingCategory -> {
            // Para o update, o mapeamento manual simples ainda é eficaz
            existingCategory.setName(dto.getName());
            Category updatedCategory = categoryRepository.save(existingCategory);
            return categoryMapper.toResponseDTO(updatedCategory); // <-- USA O MAPPER
        });
    }

    // DELETE
    @Transactional
    public boolean deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // O método de mapeamento manual foi removido!
}
