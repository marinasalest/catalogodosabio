package com.desafio.catalogodosabio.repository;

import com.desafio.catalogodosabio.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNome(String nome);
    List<Autor> findByNacionalidade(String nacionalidade);
    List<Autor> findByQtdLivrosEscritosGreaterThan(Integer quantidade);
    List<Autor> findByNomeContainingIgnoreCase(String nome);
}