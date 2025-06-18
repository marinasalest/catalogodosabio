package com.desafio.catalogodosabio.service;

import com.desafio.catalogodosabio.exception.BadRequestException;
import com.desafio.catalogodosabio.exception.ConflictException;
import com.desafio.catalogodosabio.exception.ResourceNotFoundException;
import com.desafio.catalogodosabio.model.Livro;
import com.desafio.catalogodosabio.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LivroServiceTest {
    @Mock
    private LivroRepository livroRepository;
    @InjectMocks
    private LivroService livroService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void buscarPorIdOuFalhar_DeveRetornarLivro_QuandoExiste() {
        Livro livro = new Livro();
        livro.setId(1L);
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        Livro result = livroService.buscarPorIdOuFalhar(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
    @Test
    void buscarPorIdOuFalhar_DeveLancarExcecao_QuandoNaoExiste() {
        when(livroRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> livroService.buscarPorIdOuFalhar(1L));
    }
    @Test
    void criar_DeveSalvarLivro_QuandoValido() {
        Livro livro = new Livro();
        livro.setTitulo("Livro Teste");
        when(livroRepository.save(livro)).thenReturn(livro);
        Livro result = livroService.criar(livro);
        assertNotNull(result);
        assertEquals("Livro Teste", result.getTitulo());
    }
    @Test
    void criar_DeveLancarBadRequest_QuandoTituloVazio() {
        Livro livro = new Livro();
        livro.setTitulo("");
        assertThrows(BadRequestException.class, () -> livroService.criar(livro));
    }
    @Test
    void criar_DeveLancarConflict_QuandoViolacaoIntegridade() {
        Livro livro = new Livro();
        livro.setTitulo("Livro Teste");
        when(livroRepository.save(livro)).thenThrow(new DataIntegrityViolationException("Erro"));
        assertThrows(ConflictException.class, () -> livroService.criar(livro));
    }
}