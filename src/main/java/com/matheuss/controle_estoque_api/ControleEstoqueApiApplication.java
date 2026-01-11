package com.matheuss.controle_estoque_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ControleEstoqueApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControleEstoqueApiApplication.class, args);
    }

}
