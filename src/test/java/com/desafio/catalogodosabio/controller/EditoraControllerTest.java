package com.desafio.catalogodosabio.controller;

import com.desafio.catalogodosabio.dto.EditoraResponseDTO;
import com.desafio.catalogodosabio.mapper.EditoraMapper;
import com.desafio.catalogodosabio.model.Editora;
import com.desafio.catalogodosabio.service.EditoraService;
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

class EditoraControllerTest {
    @Mock
    private EditoraService editoraService;
    @Mock
    private EditoraMapper editoraMapper;
    @InjectMocks
    private EditoraController editoraController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPublishers_DeveRetornarListaDeEditoras() {
        Editora editora = new Editora();
        editora.setId(1L);
        editora.setNome("Editora Teste");
        EditoraResponseDTO dto = new EditoraResponseDTO();
        dto.setId(1L);
        dto.setNome("Editora Teste");
        when(editoraService.listarTodos()).thenReturn(Arrays.asList(editora));
        when(editoraMapper.toResponseDTO(editora)).thenReturn(dto);

        ResponseEntity<List<EditoraResponseDTO>> response = editoraController.getAllPublishers();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        assertEquals("Editora Teste", response.getBody().get(0).getNome());
    }

    @Test
    void getPublisherById_DeveRetornarEditora_QuandoExiste() {
        Editora editora = new Editora();
        editora.setId(1L);
        editora.setNome("Editora Teste");
        EditoraResponseDTO dto = new EditoraResponseDTO();
        dto.setId(1L);
        dto.setNome("Editora Teste");
        when(editoraService.buscarPorId(1L)).thenReturn(Optional.of(editora));
        when(editoraMapper.toResponseDTO(editora)).thenReturn(dto);

        ResponseEntity<EditoraResponseDTO> response = editoraController.getPublisherById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Editora Teste", response.getBody().getNome());
    }

    @Test
    void getPublisherById_DeveRetornarNotFound_QuandoNaoExiste() {
        when(editoraService.buscarPorId(1L)).thenReturn(Optional.empty());
        ResponseEntity<EditoraResponseDTO> response = editoraController.getPublisherById(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void createPublisher_DeveCriarEditora() {
        Editora editora = new Editora();
        editora.setNome("Nova Editora");
        Editora editoraCriada = new Editora();
        editoraCriada.setId(2L);
        editoraCriada.setNome("Nova Editora");
        EditoraResponseDTO dto = new EditoraResponseDTO();
        dto.setId(2L);
        dto.setNome("Nova Editora");
        when(editoraService.criar(any(Editora.class))).thenReturn(editoraCriada);
        when(editoraMapper.toResponseDTO(editoraCriada)).thenReturn(dto);

        ResponseEntity<EditoraResponseDTO> response = editoraController.createPublisher(editora);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Nova Editora", response.getBody().getNome());
    }

    @Test
    void updatePublisher_DeveAtualizarEditora_QuandoExiste() {
        Editora editora = new Editora();
        editora.setNome("Editora Atualizada");
        Editora editoraAtualizada = new Editora();
        editoraAtualizada.setId(1L);
        editoraAtualizada.setNome("Editora Atualizada");
        EditoraResponseDTO dto = new EditoraResponseDTO();
        dto.setId(1L);
        dto.setNome("Editora Atualizada");
        when(editoraService.atualizar(eq(1L), any(Editora.class))).thenReturn(Optional.of(editoraAtualizada));
        when(editoraMapper.toResponseDTO(editoraAtualizada)).thenReturn(dto);

        ResponseEntity<EditoraResponseDTO> response = editoraController.updatePublisher(1L, editora);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Editora Atualizada", response.getBody().getNome());
    }

    @Test
    void updatePublisher_DeveRetornarNotFound_QuandoNaoExiste() {
        Editora editora = new Editora();
        when(editoraService.atualizar(eq(1L), any(Editora.class))).thenReturn(Optional.empty());
        ResponseEntity<EditoraResponseDTO> response = editoraController.updatePublisher(1L, editora);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deletePublisher_DeveDeletarEditora_QuandoExiste() {
        when(editoraService.deletar(1L)).thenReturn(true);
        ResponseEntity<Void> response = editoraController.deletePublisher(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deletePublisher_DeveRetornarNotFound_QuandoNaoExiste() {
        when(editoraService.deletar(1L)).thenReturn(false);
        ResponseEntity<Void> response = editoraController.deletePublisher(1L);
        assertEquals(404, response.getStatusCodeValue());
    }
} 