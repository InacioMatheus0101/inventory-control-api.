package com.matheuss.controle_estoque_api.repository;

import com.matheuss.controle_estoque_api.domain.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {
    // Por enquanto, os métodos padrão do JpaRepository (findAll, findById, save, etc.)
    // são suficientes para o nosso CRUD.
}
