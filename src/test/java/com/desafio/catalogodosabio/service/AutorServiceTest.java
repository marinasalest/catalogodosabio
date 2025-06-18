package com.desafio.catalogodosabio.service;

import com.desafio.catalogodosabio.exception.BadRequestException;
import com.desafio.catalogodosabio.exception.ConflictException;
import com.desafio.catalogodosabio.exception.ResourceNotFoundException;
import com.desafio.catalogodosabio.model.Autor;
import com.desafio.catalogodosabio.repository.AutorRepository;
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

class AutorServiceTest {
    @Mock
    private AutorRepository autorRepository;
    @InjectMocks
    private AutorService autorService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void listarTodos_DeveRetornarLista() {
        when(autorRepository.findAll()).thenReturn(Collections.singletonList(new Autor()));
        List<Autor> result = autorService.listarTodos();
        assertFalse(result.isEmpty());
    }
    @Test
    void buscarPorIdOuFalhar_DeveRetornarAutor_QuandoExiste() {
        Autor autor = new Autor();
        autor.setId(1L);
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        Autor result = autorService.buscarPorIdOuFalhar(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
    @Test
    void buscarPorIdOuFalhar_DeveLancarExcecao_QuandoNaoExiste() {
        when(autorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> autorService.buscarPorIdOuFalhar(1L));
    }
    @Test
    void criar_DeveSalvarAutor_QuandoValido() {
        Autor autor = new Autor();
        autor.setNome("Autor Teste");
        when(autorRepository.save(autor)).thenReturn(autor);
        Autor result = autorService.criar(autor);
        assertNotNull(result);
        assertEquals("Autor Teste", result.getNome());
    }
    @Test
    void criar_DeveLancarBadRequest_QuandoNomeVazio() {
        Autor autor = new Autor();
        autor.setNome("");
        assertThrows(BadRequestException.class, () -> autorService.criar(autor));
    }
    @Test
    void criar_DeveLancarConflict_QuandoViolacaoIntegridade() {
        Autor autor = new Autor();
        autor.setNome("Autor Teste");
        when(autorRepository.save(autor)).thenThrow(new DataIntegrityViolationException("Erro"));
        assertThrows(ConflictException.class, () -> autorService.criar(autor));
    }
    @Test
    void atualizar_DeveAtualizarAutor_QuandoExiste() {
        Autor autor = new Autor();
        autor.setId(1L);
        Autor atualizado = new Autor();
        atualizado.setNome("Novo Nome");
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(autorRepository.save(any(Autor.class))).thenReturn(autor);
        Optional<Autor> result = autorService.atualizar(1L, atualizado);
        assertTrue(result.isPresent());
        assertEquals("Novo Nome", result.get().getNome());
    }
    @Test
    void atualizar_DeveRetornarVazio_QuandoNaoExiste() {
        Autor atualizado = new Autor();
        when(autorRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Autor> result = autorService.atualizar(1L, atualizado);
        assertFalse(result.isPresent());
    }
    @Test
    void deletar_DeveRetornarTrue_QuandoExiste() {
        when(autorRepository.existsById(1L)).thenReturn(true);
        boolean result = autorService.deletar(1L);
        assertTrue(result);
        verify(autorRepository).deleteById(1L);
    }
    @Test
    void deletar_DeveRetornarFalse_QuandoNaoExiste() {
        when(autorRepository.existsById(1L)).thenReturn(false);
        boolean result = autorService.deletar(1L);
        assertFalse(result);
    }
}