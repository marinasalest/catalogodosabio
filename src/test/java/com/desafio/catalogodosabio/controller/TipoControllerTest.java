package com.desafio.catalogodosabio.controller;

import com.desafio.catalogodosabio.dto.TipoResponseDTO;
import com.desafio.catalogodosabio.mapper.TipoMapper;
import com.desafio.catalogodosabio.model.Tipo;
import com.desafio.catalogodosabio.service.TipoService;
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

class TipoControllerTest {
    @Mock
    private TipoService tipoService;
    @Mock
    private TipoMapper tipoMapper;
    @InjectMocks
    private TipoController tipoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTypes_DeveRetornarListaDeTipos() {
        Tipo tipo = new Tipo();
        tipo.setId(1L);
        tipo.setNome("Impresso");
        TipoResponseDTO dto = new TipoResponseDTO();
        dto.setId(1L);
        dto.setNome("Impresso");
        when(tipoService.listarTodos()).thenReturn(Arrays.asList(tipo));
        when(tipoMapper.toResponseDTO(tipo)).thenReturn(dto);

        ResponseEntity<List<TipoResponseDTO>> response = tipoController.getAllTypes();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
        assertEquals("Impresso", response.getBody().get(0).getNome());
    }

    @Test
    void getTypeById_DeveRetornarTipo_QuandoExiste() {
        Tipo tipo = new Tipo();
        tipo.setId(1L);
        tipo.setNome("Impresso");
        TipoResponseDTO dto = new TipoResponseDTO();
        dto.setId(1L);
        dto.setNome("Impresso");
        when(tipoService.buscarPorId(1L)).thenReturn(Optional.of(tipo));
        when(tipoMapper.toResponseDTO(tipo)).thenReturn(dto);

        ResponseEntity<TipoResponseDTO> response = tipoController.getTypeById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Impresso", response.getBody().getNome());
    }

    @Test
    void getTypeById_DeveRetornarNotFound_QuandoNaoExiste() {
        when(tipoService.buscarPorId(1L)).thenReturn(Optional.empty());
        ResponseEntity<TipoResponseDTO> response = tipoController.getTypeById(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void createType_DeveCriarTipo() {
        Tipo tipo = new Tipo();
        tipo.setNome("Digital");
        Tipo tipoCriado = new Tipo();
        tipoCriado.setId(2L);
        tipoCriado.setNome("Digital");
        TipoResponseDTO dto = new TipoResponseDTO();
        dto.setId(2L);
        dto.setNome("Digital");
        when(tipoService.criar(any(Tipo.class))).thenReturn(tipoCriado);
        when(tipoMapper.toResponseDTO(tipoCriado)).thenReturn(dto);

        ResponseEntity<TipoResponseDTO> response = tipoController.createType(tipo);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Digital", response.getBody().getNome());
    }

    @Test
    void updateType_DeveAtualizarTipo_QuandoExiste() {
        Tipo tipo = new Tipo();
        tipo.setNome("Audiobook");
        Tipo tipoAtualizado = new Tipo();
        tipoAtualizado.setId(1L);
        tipoAtualizado.setNome("Audiobook");
        TipoResponseDTO dto = new TipoResponseDTO();
        dto.setId(1L);
        dto.setNome("Audiobook");
        when(tipoService.atualizar(eq(1L), any(Tipo.class))).thenReturn(Optional.of(tipoAtualizado));
        when(tipoMapper.toResponseDTO(tipoAtualizado)).thenReturn(dto);

        ResponseEntity<TipoResponseDTO> response = tipoController.updateType(1L, tipo);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Audiobook", response.getBody().getNome());
    }

    @Test
    void updateType_DeveRetornarNotFound_QuandoNaoExiste() {
        Tipo tipo = new Tipo();
        when(tipoService.atualizar(eq(1L), any(Tipo.class))).thenReturn(Optional.empty());
        ResponseEntity<TipoResponseDTO> response = tipoController.updateType(1L, tipo);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void deleteType_DeveDeletarTipo_QuandoExiste() {
        when(tipoService.deletar(1L)).thenReturn(true);
        ResponseEntity<Void> response = tipoController.deleteType(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deleteType_DeveRetornarNotFound_QuandoNaoExiste() {
        when(tipoService.deletar(1L)).thenReturn(false);
        ResponseEntity<Void> response = tipoController.deleteType(1L);
        assertEquals(404, response.getStatusCodeValue());
    }
} 