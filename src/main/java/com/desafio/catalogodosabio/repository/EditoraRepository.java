package com.desafio.catalogodosabio.repository;

import com.desafio.catalogodosabio.model.Editora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EditoraRepository extends JpaRepository<Editora, Long> {
    Optional<Editora> findByCnpj(String cnpj);
    Optional<Editora> findByNome(String nome);
    Optional<Editora> findByEmail(String email);
}