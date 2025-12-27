package com.matheuss.controle_estoque_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matheuss.controle_estoque_api.domain.Computer;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {
}
