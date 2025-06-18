package com.desafio.catalogodosabio.service;

import com.desafio.catalogodosabio.exception.BadRequestException;
import com.desafio.catalogodosabio.exception.ConflictException;
import com.desafio.catalogodosabio.exception.ResourceNotFoundException;
import com.desafio.catalogodosabio.model.Genero;
import com.desafio.catalogodosabio.repository.GeneroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GeneroServiceTest {
    @Mock
    private GeneroRepository generoRepository;
    @InjectMocks
    private GeneroService generoService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void listarTodos_DeveRetornarLista() {
        when(generoRepository.findAll()).thenReturn(Collections.singletonList(new Genero()));
        List<Genero> result = generoService.listarTodos();
        assertFalse(result.isEmpty());
    }
    @Test
    void buscarPorIdOuFalhar_DeveRetornarGenero_QuandoExiste() {
        Genero genero = new Genero();
        genero.setId(1L);
        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));
        Genero result = generoService.buscarPorIdOuFalhar(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
    @Test
    void buscarPorIdOuFalhar_DeveLancarExcecao_QuandoNaoExiste() {
        when(generoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> generoService.buscarPorIdOuFalhar(1L));
    }
    @Test
    void criar_DeveSalvarGenero_QuandoValido() {
        Genero genero = new Genero();
        genero.setNome("Genero Teste");
        when(generoRepository.save(genero)).thenReturn(genero);
        Genero result = generoService.criar(genero);
        assertNotNull(result);
        assertEquals("Genero Teste", result.getNome());
    }
    @Test
    void criar_DeveLancarBadRequest_QuandoNomeVazio() {
        Genero genero = new Genero();
        genero.setNome("");
        assertThrows(BadRequestException.class, () -> generoService.criar(genero));
    }
    @Test
    void criar_DeveLancarConflict_QuandoViolacaoIntegridade() {
        Genero genero = new Genero();
        genero.setNome("Genero Teste");
        when(generoRepository.save(genero)).thenThrow(new DataIntegrityViolationException("Erro"));
        assertThrows(ConflictException.class, () -> generoService.criar(genero));
    }
    @Test
    void atualizar_DeveAtualizarGenero_QuandoExiste() {
        Genero genero = new Genero();
        genero.setId(1L);
        Genero atualizado = new Genero();
        atualizado.setNome("Novo Nome");
        when(generoRepository.findById(1L)).thenReturn(Optional.of(genero));
        when(generoRepository.save(any(Genero.class))).thenReturn(genero);
        Optional<Genero> result = generoService.atualizar(1L, atualizado);
        assertTrue(result.isPresent());
        assertEquals("Novo Nome", result.get().getNome());
    }
    @Test
    void atualizar_DeveRetornarVazio_QuandoNaoExiste() {
        Genero atualizado = new Genero();
        when(generoRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Genero> result = generoService.atualizar(1L, atualizado);
        assertFalse(result.isPresent());
    }
    @Test
    void deletar_DeveRetornarTrue_QuandoExiste() {
        when(generoRepository.existsById(1L)).thenReturn(true);
        boolean result = generoService.deletar(1L);
        assertTrue(result);
        verify(generoRepository).deleteById(1L);
    }
    @Test
    void deletar_DeveRetornarFalse_QuandoNaoExiste() {
        when(generoRepository.existsById(1L)).thenReturn(false);
        boolean result = generoService.deletar(1L);
        assertFalse(result);
    }
}