package com.desafio.catalogodosabio.controller;

import com.desafio.catalogodosabio.dto.IdiomaResponseDTO;
import com.desafio.catalogodosabio.mapper.IdiomaMapper;
import com.desafio.catalogodosabio.model.Idioma;
import com.desafio.catalogodosabio.service.IdiomaService;
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

class IdiomaControllerTest {
    @Mock
    private IdiomaService idiomaService;
    @Mock
    private IdiomaMapper idiomaMapper;
    @InjectMocks
    private IdiomaController idiomaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllLanguages_DeveRetornarListaDeIdiomas() {
        Idioma idioma = new Idioma();
        idioma.setId(1L);
        idioma.setNome("Português");
        IdiomaResponseDTO dto = new IdiomaResponseDTO();
        dto.setId(1L);
        dto.setNome("Português");
        when(idiomaService.listarTodos()).thenReturn(Arrays.asList(idioma));
        when(idiomaMapper.toResponseDTO(idioma)).thenReturn(dto);

        ResponseEntity<List<IdiomaResponseDTO>> response = idiomaController.getAllLanguages();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        assertEquals("Português", response.getBody().get(0).getNome());
    }

    @Test
    void getLanguageById_DeveRetornarIdioma_QuandoExiste() {
        Idioma idioma = new Idioma();
        idioma.setId(1L);
        idioma.setNome("Português");
        IdiomaResponseDTO dto = new IdiomaResponseDTO();
        dto.setId(1L);
        dto.setNome("Português");
        when(idiomaService.buscarPorId(1L)).thenReturn(Optional.of(idioma));
        when(idiomaMapper.toResponseDTO(idioma)).thenReturn(dto);

        ResponseEntity<IdiomaResponseDTO> response = idiomaController.getLanguageById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Português", response.getBody().getNome());
    }

    @Test
    void getLanguageById_DeveRetornarNotFound_QuandoNaoExiste() {
        when(idiomaService.buscarPorId(1L)).thenReturn(Optional.empty());
        ResponseEntity<IdiomaResponseDTO> response = idiomaController.getLanguageById(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void createLanguage_DeveCriarIdioma() {
        Idioma idioma = new Idioma();
        idioma.setNome("Inglês");
        Idioma idiomaCriado = new Idioma();
        idiomaCriado.setId(2L);
        idiomaCriado.setNome("Inglês");
        IdiomaResponseDTO dto = new IdiomaResponseDTO();
        dto.setId(2L);
        dto.setNome("Inglês");
        when(idiomaService.criar(any(Idioma.class))).thenReturn(idiomaCriado);
        when(idiomaMapper.toResponseDTO(idiomaCriado)).thenReturn(dto);

        ResponseEntity<IdiomaResponseDTO> response = idiomaController.createLanguage(idioma);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Inglês", response.getBody().getNome());
    }

    @Test
    void updateLanguage_DeveAtualizarIdioma_QuandoExiste() {
        Idioma idioma = new Idioma();
        idioma.setNome("Espanhol");
        Idioma idiomaAtualizado = new Idioma();
        idiomaAtualizado.setId(1L);
        idiomaAtualizado.setNome("Espanhol");
        IdiomaResponseDTO dto = new IdiomaResponseDTO();
        dto.setId(1L);
        dto.setNome("Espanhol");
        when(idiomaService.atualizar(eq(1L), any(Idioma.class))).thenReturn(Optional.of(idiomaAtualizado));
        when(idiomaMapper.toResponseDTO(idiomaAtualizado)).thenReturn(dto);

        ResponseEntity<IdiomaResponseDTO> response = idiomaController.updateLanguage(1L, idioma);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Espanhol", response.getBody().getNome());
    }

    @Test
    void updateLanguage_DeveRetornarNotFound_QuandoNaoExiste() {
        Idioma idioma = new Idioma();
        when(idiomaService.atualizar(eq(1L), any(Idioma.class))).thenReturn(Optional.empty());
        ResponseEntity<IdiomaResponseDTO> response = idiomaController.updateLanguage(1L, idioma);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deleteLanguage_DeveDeletarIdioma_QuandoExiste() {
        when(idiomaService.deletar(1L)).thenReturn(true);
        ResponseEntity<Void> response = idiomaController.deleteLanguage(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deleteLanguage_DeveRetornarNotFound_QuandoNaoExiste() {
        when(idiomaService.deletar(1L)).thenReturn(false);
        ResponseEntity<Void> response = idiomaController.deleteLanguage(1L);
        assertEquals(404, response.getStatusCodeValue());
    }
} 