package com.desafio.catalogodosabio.repository;

import com.desafio.catalogodosabio.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByTituloIgnoreCase(String titulo);

    List<Livro> findByAutorId(Long autorId);

    List<Livro> findByGeneroId(Long generoId);

    List<Livro> findByEditoraId(Long editoraId);

    List<Livro> findByTipoId(Long tipoId);

    List<Livro> findByIdiomaId(Long idiomaId);

    List<Livro> findByPrecoBetween(BigDecimal precoMin, BigDecimal precoMax);

    List<Livro> findByAno(Integer ano);

    List<Livro> findByQtdGreaterThan(Integer quantidade);

    Page<Livro> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

    @Query("SELECT l FROM Livro l WHERE l.genero.id IN :generoIds")
    List<Livro> findByGeneros(@Param("generoIds") List<Long> generoIds);

    @Query("SELECT l FROM Livro l WHERE l.autor.id IN :autorIds")
    List<Livro> findByAutores(@Param("autorIds") List<Long> autorIds);

    @Query("SELECT l FROM Livro l WHERE LOWER(l.sinopse) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Livro> findBySinopseContaining(@Param("termo") String termo);

    List<Livro> findByGeneroNomeIgnoreCase(String nomeGenero);
    List<Livro> findByAutorNomeIgnoreCase(String nomeAutor);
}