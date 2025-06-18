package com.desafio.catalogodosabio.controller;

import com.desafio.catalogodosabio.dto.GeneroResponseDTO;
import com.desafio.catalogodosabio.mapper.GeneroMapper;
import com.desafio.catalogodosabio.model.Genero;
import com.desafio.catalogodosabio.service.GeneroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GeneroControllerTest {
    @Mock
    private GeneroService generoService;
    @Mock
    private GeneroMapper generoMapper;
    @InjectMocks
    private GeneroController generoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllGenres_DeveRetornarListaDeGeneros() {
        Genero genero = new Genero();
        genero.setId(1L);
        genero.setNome("Ficção");
        GeneroResponseDTO dto = new GeneroResponseDTO();
        dto.setId(1L);
        dto.setNome("Ficção");
        when(generoService.listarTodos()).thenReturn(Arrays.asList(genero));
        when(generoMapper.toResponseDTO(genero)).thenReturn(dto);

        ResponseEntity<List<GeneroResponseDTO>> response = generoController.getAllGenres();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        assertEquals("Ficção", response.getBody().get(0).getNome());
    }

    @Test
    void getGenreById_DeveRetornarGenero_QuandoExiste() {
        Genero genero = new Genero();
        genero.setId(1L);
        genero.setNome("Ficção");
        GeneroResponseDTO dto = new GeneroResponseDTO();
        dto.setId(1L);
        dto.setNome("Ficção");
        when(generoService.buscarPorId(1L)).thenReturn(Optional.of(genero));
        when(generoMapper.toResponseDTO(genero)).thenReturn(dto);

        ResponseEntity<GeneroResponseDTO> response = generoController.getGenreById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Ficção", response.getBody().getNome());
    }

    @Test
    void getGenreById_DeveRetornarNotFound_QuandoNaoExiste() {
        when(generoService.buscarPorId(1L)).thenReturn(Optional.empty());
        ResponseEntity<GeneroResponseDTO> response = generoController.getGenreById(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void createGenre_DeveCriarGenero() {
        Genero genero = new Genero();
        genero.setNome("Aventura");
        Genero generoCriado = new Genero();
        generoCriado.setId(2L);
        generoCriado.setNome("Aventura");
        GeneroResponseDTO dto = new GeneroResponseDTO();
        dto.setId(2L);
        dto.setNome("Aventura");
        when(generoService.criar(any(Genero.class))).thenReturn(generoCriado);
        when(generoMapper.toResponseDTO(generoCriado)).thenReturn(dto);

        ResponseEntity<GeneroResponseDTO> response = generoController.createGenre(genero);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Aventura", response.getBody().getNome());
    }

    @Test
    void updateGenre_DeveAtualizarGenero_QuandoExiste() {
        Genero genero = new Genero();
        genero.setNome("Romance");
        Genero generoAtualizado = new Genero();
        generoAtualizado.setId(1L);
        generoAtualizado.setNome("Romance");
        GeneroResponseDTO dto = new GeneroResponseDTO();
        dto.setId(1L);
        dto.setNome("Romance");
        when(generoService.atualizar(eq(1L), any(Genero.class))).thenReturn(Optional.of(generoAtualizado));
        when(generoMapper.toResponseDTO(generoAtualizado)).thenReturn(dto);

        ResponseEntity<GeneroResponseDTO> response = generoController.updateGenre(1L, genero);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Romance", response.getBody().getNome());
    }

    @Test
    void updateGenre_DeveRetornarNotFound_QuandoNaoExiste() {
        Genero genero = new Genero();
        when(generoService.atualizar(eq(1L), any(Genero.class))).thenReturn(Optional.empty());
        ResponseEntity<GeneroResponseDTO> response = generoController.updateGenre(1L, genero);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deleteGenre_DeveDeletarGenero_QuandoExiste() {
        when(generoService.deletar(1L)).thenReturn(true);
        ResponseEntity<Void> response = generoController.deleteGenre(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deleteGenre_DeveRetornarNotFound_QuandoNaoExiste() {
        when(generoService.deletar(1L)).thenReturn(false);
        ResponseEntity<Void> response = generoController.deleteGenre(1L);
        assertEquals(404, response.getStatusCodeValue());
    }
} 