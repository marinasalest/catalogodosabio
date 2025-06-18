package com.desafio.catalogodosabio.controller;

import com.desafio.catalogodosabio.dto.AutorResponseDTO;
import com.desafio.catalogodosabio.mapper.AutorMapper;
import com.desafio.catalogodosabio.model.Autor;
import com.desafio.catalogodosabio.service.AutorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AutorControllerTest {
    @Mock
    private AutorService autorService;
    @Mock
    private AutorMapper autorMapper;
    @InjectMocks
    private AutorController autorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAuthors_DeveRetornarListaDeAutores() {
        List<Autor> autores = Collections.singletonList(new Autor());
        when(autorService.listarTodos()).thenReturn(autores);
        when(autorMapper.toResponseDTO(any(Autor.class))).thenReturn(new AutorResponseDTO());

        ResponseEntity<List<AutorResponseDTO>> response = autorController.getAllAuthors();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void getAuthorById_DeveRetornarAutor_QuandoExiste() {
        Long id = 1L;
        Autor autor = new Autor();
        AutorResponseDTO dto = new AutorResponseDTO();
        when(autorService.buscarPorId(id)).thenReturn(Optional.of(autor));
        when(autorMapper.toResponseDTO(autor)).thenReturn(dto);

        ResponseEntity<AutorResponseDTO> response = autorController.getAuthorById(id);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void getAuthorById_DeveRetornarNotFound_QuandoNaoExiste() {
        Long id = 1L;
        when(autorService.buscarPorId(id)).thenReturn(Optional.empty());

        ResponseEntity<AutorResponseDTO> response = autorController.getAuthorById(id);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void createAuthor_DeveCriarAutor() {
        Autor autor = new Autor();
        AutorResponseDTO dto = new AutorResponseDTO();
        when(autorService.criar(autor)).thenReturn(autor);
        when(autorMapper.toResponseDTO(autor)).thenReturn(dto);

        ResponseEntity<AutorResponseDTO> response = autorController.createAuthor(autor);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(autorService).criar(autor);
    }

    @Test
    void updateAuthor_DeveAtualizarAutor_QuandoExiste() {
        Long id = 1L;
        Autor autor = new Autor();
        AutorResponseDTO dto = new AutorResponseDTO();
        when(autorService.atualizar(id, autor)).thenReturn(Optional.of(autor));
        when(autorMapper.toResponseDTO(autor)).thenReturn(dto);

        ResponseEntity<AutorResponseDTO> response = autorController.updateAuthor(id, autor);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void updateAuthor_DeveRetornarNotFound_QuandoNaoExiste() {
        Long id = 1L;
        Autor autor = new Autor();
        when(autorService.atualizar(id, autor)).thenReturn(Optional.empty());

        ResponseEntity<AutorResponseDTO> response = autorController.updateAuthor(id, autor);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void deleteAuthor_DeveDeletarAutor_QuandoExiste() {
        Long id = 1L;
        when(autorService.deletar(id)).thenReturn(true);

        ResponseEntity<Void> response = autorController.deleteAuthor(id);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deleteAuthor_DeveRetornarNotFound_QuandoNaoExiste() {
        Long id = 1L;
        when(autorService.deletar(id)).thenReturn(false);

        ResponseEntity<Void> response = autorController.deleteAuthor(id);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
} 