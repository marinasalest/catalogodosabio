package com.desafio.catalogodosabio.repository;

import com.desafio.catalogodosabio.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, Long> {
    Optional<Idioma> findByNome(String nome);
}