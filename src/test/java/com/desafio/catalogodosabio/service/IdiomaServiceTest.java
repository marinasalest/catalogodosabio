package com.desafio.catalogodosabio.service;

import com.desafio.catalogodosabio.exception.BadRequestException;
import com.desafio.catalogodosabio.exception.ConflictException;
import com.desafio.catalogodosabio.exception.ResourceNotFoundException;
import com.desafio.catalogodosabio.model.Idioma;
import com.desafio.catalogodosabio.repository.IdiomaRepository;
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

class IdiomaServiceTest {
    @Mock
    private IdiomaRepository idiomaRepository;
    @InjectMocks
    private IdiomaService idiomaService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void listarTodos_DeveRetornarLista() {
        when(idiomaRepository.findAll()).thenReturn(Collections.singletonList(new Idioma()));
        List<Idioma> result = idiomaService.listarTodos();
        assertFalse(result.isEmpty());
    }
    @Test
    void buscarPorIdOuFalhar_DeveRetornarIdioma_QuandoExiste() {
        Idioma idioma = new Idioma();
        idioma.setId(1L);
        when(idiomaRepository.findById(1L)).thenReturn(Optional.of(idioma));
        Idioma result = idiomaService.buscarPorIdOuFalhar(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
    @Test
    void buscarPorIdOuFalhar_DeveLancarExcecao_QuandoNaoExiste() {
        when(idiomaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> idiomaService.buscarPorIdOuFalhar(1L));
    }
    @Test
    void criar_DeveSalvarIdioma_QuandoValido() {
        Idioma idioma = new Idioma();
        idioma.setNome("Idioma Teste");
        when(idiomaRepository.save(idioma)).thenReturn(idioma);
        Idioma result = idiomaService.criar(idioma);
        assertNotNull(result);
        assertEquals("Idioma Teste", result.getNome());
    }
    @Test
    void criar_DeveLancarBadRequest_QuandoNomeVazio() {
        Idioma idioma = new Idioma();
        idioma.setNome("");
        assertThrows(BadRequestException.class, () -> idiomaService.criar(idioma));
    }
    @Test
    void criar_DeveLancarConflict_QuandoViolacaoIntegridade() {
        Idioma idioma = new Idioma();
        idioma.setNome("Idioma Teste");
        when(idiomaRepository.save(idioma)).thenThrow(new DataIntegrityViolationException("Erro"));
        assertThrows(ConflictException.class, () -> idiomaService.criar(idioma));
    }
    @Test
    void atualizar_DeveAtualizarIdioma_QuandoExiste() {
        Idioma idioma = new Idioma();
        idioma.setId(1L);
        Idioma atualizado = new Idioma();
        atualizado.setNome("Novo Nome");
        when(idiomaRepository.findById(1L)).thenReturn(Optional.of(idioma));
        when(idiomaRepository.save(any(Idioma.class))).thenReturn(idioma);
        Optional<Idioma> result = idiomaService.atualizar(1L, atualizado);
        assertTrue(result.isPresent());
        assertEquals("Novo Nome", result.get().getNome());
    }
    @Test
    void atualizar_DeveRetornarVazio_QuandoNaoExiste() {
        Idioma atualizado = new Idioma();
        when(idiomaRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Idioma> result = idiomaService.atualizar(1L, atualizado);
        assertFalse(result.isPresent());
    }
    @Test
    void deletar_DeveRetornarTrue_QuandoExiste() {
        when(idiomaRepository.existsById(1L)).thenReturn(true);
        boolean result = idiomaService.deletar(1L);
        assertTrue(result);
        verify(idiomaRepository).deleteById(1L);
    }
    @Test
    void deletar_DeveRetornarFalse_QuandoNaoExiste() {
        when(idiomaRepository.existsById(1L)).thenReturn(false);
        boolean result = idiomaService.deletar(1L);
        assertFalse(result);
    }
}