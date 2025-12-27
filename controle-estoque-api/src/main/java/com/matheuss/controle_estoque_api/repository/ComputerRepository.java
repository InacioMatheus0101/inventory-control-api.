package com.matheuss.controle_estoque_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param; // Importe esta classe
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.matheuss.controle_estoque_api.domain.Computer;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {

    @Override
    @NonNull // <- ADICIONE ESTA LINHA
    @Query("SELECT c FROM Computer c JOIN FETCH c.category LEFT JOIN FETCH c.supplier")
    List<Computer> findAll();

    @Query("SELECT c FROM Computer c JOIN FETCH c.category LEFT JOIN FETCH c.supplier WHERE c.id = :id")
    Optional<Computer> findByIdWithDetails(@Param("id") Long id);
}
