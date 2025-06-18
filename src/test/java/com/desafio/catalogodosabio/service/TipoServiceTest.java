package com.desafio.catalogodosabio.service;

import com.desafio.catalogodosabio.exception.BadRequestException;
import com.desafio.catalogodosabio.exception.ConflictException;
import com.desafio.catalogodosabio.exception.ResourceNotFoundException;
import com.desafio.catalogodosabio.model.Tipo;
import com.desafio.catalogodosabio.repository.TipoRepository;
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

class TipoServiceTest {
    @Mock
    private TipoRepository tipoRepository;
    @InjectMocks
    private TipoService tipoService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void listarTodos_DeveRetornarLista() {
        when(tipoRepository.findAll()).thenReturn(Collections.singletonList(new Tipo()));
        List<Tipo> result = tipoService.listarTodos();
        assertFalse(result.isEmpty());
    }
    @Test
    void buscarPorIdOuFalhar_DeveRetornarTipo_QuandoExiste() {
        Tipo tipo = new Tipo();
        tipo.setId(1L);
        when(tipoRepository.findById(1L)).thenReturn(Optional.of(tipo));
        Tipo result = tipoService.buscarPorIdOuFalhar(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
    @Test
    void buscarPorIdOuFalhar_DeveLancarExcecao_QuandoNaoExiste() {
        when(tipoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> tipoService.buscarPorIdOuFalhar(1L));
    }
    @Test
    void criar_DeveSalvarTipo_QuandoValido() {
        Tipo tipo = new Tipo();
        tipo.setNome("Tipo Teste");
        when(tipoRepository.save(tipo)).thenReturn(tipo);
        Tipo result = tipoService.criar(tipo);
        assertNotNull(result);
        assertEquals("Tipo Teste", result.getNome());
    }
    @Test
    void criar_DeveLancarBadRequest_QuandoNomeVazio() {
        Tipo tipo = new Tipo();
        tipo.setNome("");
        assertThrows(BadRequestException.class, () -> tipoService.criar(tipo));
    }
    @Test
    void criar_DeveLancarConflict_QuandoViolacaoIntegridade() {
        Tipo tipo = new Tipo();
        tipo.setNome("Tipo Teste");
        when(tipoRepository.save(tipo)).thenThrow(new DataIntegrityViolationException("Erro"));
        assertThrows(ConflictException.class, () -> tipoService.criar(tipo));
    }
    @Test
    void atualizar_DeveAtualizarTipo_QuandoExiste() {
        Tipo tipo = new Tipo();
        tipo.setId(1L);
        Tipo atualizado = new Tipo();
        atualizado.setNome("Novo Nome");
        when(tipoRepository.findById(1L)).thenReturn(Optional.of(tipo));
        when(tipoRepository.save(any(Tipo.class))).thenReturn(tipo);
        Optional<Tipo> result = tipoService.atualizar(1L, atualizado);
        assertTrue(result.isPresent());
        assertEquals("Novo Nome", result.get().getNome());
    }
    @Test
    void atualizar_DeveRetornarVazio_QuandoNaoExiste() {
        Tipo atualizado = new Tipo();
        when(tipoRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Tipo> result = tipoService.atualizar(1L, atualizado);
        assertFalse(result.isPresent());
    }
    @Test
    void deletar_DeveRetornarTrue_QuandoExiste() {
        when(tipoRepository.existsById(1L)).thenReturn(true);
        boolean result = tipoService.deletar(1L);
        assertTrue(result);
        verify(tipoRepository).deleteById(1L);
    }
    @Test
    void deletar_DeveRetornarFalse_QuandoNaoExiste() {
        when(tipoRepository.existsById(1L)).thenReturn(false);
        boolean result = tipoService.deletar(1L);
        assertFalse(result);
    }
}