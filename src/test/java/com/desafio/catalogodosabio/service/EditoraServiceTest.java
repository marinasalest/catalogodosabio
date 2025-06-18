package com.desafio.catalogodosabio.service;

import com.desafio.catalogodosabio.exception.BadRequestException;
import com.desafio.catalogodosabio.exception.ConflictException;
import com.desafio.catalogodosabio.exception.ResourceNotFoundException;
import com.desafio.catalogodosabio.model.Editora;
import com.desafio.catalogodosabio.repository.EditoraRepository;
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

class EditoraServiceTest {
    @Mock
    private EditoraRepository editoraRepository;
    @InjectMocks
    private EditoraService editoraService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void listarTodos_DeveRetornarLista() {
        when(editoraRepository.findAll()).thenReturn(Collections.singletonList(new Editora()));
        List<Editora> result = editoraService.listarTodos();
        assertFalse(result.isEmpty());
    }
    @Test
    void buscarPorIdOuFalhar_DeveRetornarEditora_QuandoExiste() {
        Editora editora = new Editora();
        editora.setId(1L);
        when(editoraRepository.findById(1L)).thenReturn(Optional.of(editora));
        Editora result = editoraService.buscarPorIdOuFalhar(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
    @Test
    void buscarPorIdOuFalhar_DeveLancarExcecao_QuandoNaoExiste() {
        when(editoraRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> editoraService.buscarPorIdOuFalhar(1L));
    }
    @Test
    void criar_DeveSalvarEditora_QuandoValido() {
        Editora editora = new Editora();
        editora.setNome("Editora Teste");
        when(editoraRepository.save(editora)).thenReturn(editora);
        Editora result = editoraService.criar(editora);
        assertNotNull(result);
        assertEquals("Editora Teste", result.getNome());
    }
    @Test
    void criar_DeveLancarBadRequest_QuandoNomeVazio() {
        Editora editora = new Editora();
        editora.setNome("");
        assertThrows(BadRequestException.class, () -> editoraService.criar(editora));
    }
    @Test
    void criar_DeveLancarConflict_QuandoViolacaoIntegridade() {
        Editora editora = new Editora();
        editora.setNome("Editora Teste");
        when(editoraRepository.save(editora)).thenThrow(new DataIntegrityViolationException("Erro"));
        assertThrows(ConflictException.class, () -> editoraService.criar(editora));
    }
    @Test
    void atualizar_DeveAtualizarEditora_QuandoExiste() {
        Editora editora = new Editora();
        editora.setId(1L);
        Editora atualizado = new Editora();
        atualizado.setNome("Novo Nome");
        when(editoraRepository.findById(1L)).thenReturn(Optional.of(editora));
        when(editoraRepository.save(any(Editora.class))).thenReturn(editora);
        Optional<Editora> result = editoraService.atualizar(1L, atualizado);
        assertTrue(result.isPresent());
        assertEquals("Novo Nome", result.get().getNome());
    }
    @Test
    void atualizar_DeveRetornarVazio_QuandoNaoExiste() {
        Editora atualizado = new Editora();
        when(editoraRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Editora> result = editoraService.atualizar(1L, atualizado);
        assertFalse(result.isPresent());
    }
    @Test
    void deletar_DeveRetornarTrue_QuandoExiste() {
        when(editoraRepository.existsById(1L)).thenReturn(true);
        boolean result = editoraService.deletar(1L);
        assertTrue(result);
        verify(editoraRepository).deleteById(1L);
    }
    @Test
    void deletar_DeveRetornarFalse_QuandoNaoExiste() {
        when(editoraRepository.existsById(1L)).thenReturn(false);
        boolean result = editoraService.deletar(1L);
        assertFalse(result);
    }
}