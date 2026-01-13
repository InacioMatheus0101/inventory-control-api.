package com.matheuss.controle_estoque_api.repository;

import com.matheuss.controle_estoque_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // O Spring Data JPA criará os métodos básicos (findById, findAll, save, etc.) automaticamente.
    // Podemos adicionar métodos customizados aqui no futuro, se necessário.
}
