package com.desafio.catalogodosabio.repository;

import com.desafio.catalogodosabio.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {
    Optional<Genero> findByNome(String nome);
}