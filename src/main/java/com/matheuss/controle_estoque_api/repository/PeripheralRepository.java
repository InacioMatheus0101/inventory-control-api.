package com.matheuss.controle_estoque_api.repository;

import com.matheuss.controle_estoque_api.domain.Peripheral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeripheralRepository extends JpaRepository<Peripheral, Long> {
    // Por enquanto, os métodos padrão do JpaRepository (findAll, findById, save, etc.)
    // são suficientes para construir nosso CRUD.
    // No futuro, se precisarmos de buscas customizadas (ex: "buscar todos os mouses"),
    // adicionaremos os métodos aqui.
}
