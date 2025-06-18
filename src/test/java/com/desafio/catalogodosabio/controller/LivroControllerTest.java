package com.desafio.catalogodosabio.controller;

import com.desafio.catalogodosabio.dto.LivroResponseDTO;
import com.desafio.catalogodosabio.mapper.LivroMapper;
import com.desafio.catalogodosabio.model.Livro;
import com.desafio.catalogodosabio.service.LivroService;
import com.desafio.catalogodosabio.service.RecentViewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LivroControllerTest {
    @Mock
    private LivroService livroService;
    @Mock
    private LivroMapper livroMapper;
    @Mock
    private RecentViewService recentViewService;
    @InjectMocks
    private LivroController livroController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBooks_DeveRetornarPaginaDeLivros() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Livro> livrosPage = new PageImpl<>(Collections.singletonList(new Livro()));
        when(livroService.listarTodosPaginado(pageable)).thenReturn(livrosPage);
        when(livroMapper.toResponseDTO(any(Livro.class))).thenReturn(new LivroResponseDTO());

        ResponseEntity<Page<LivroResponseDTO>> response = livroController.getAllBooks(0, 10);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(livroService).listarTodosPaginado(pageable);
    }

    @Test
    void getBookById_DeveRetornarLivro_QuandoExiste() {
        Long id = 1L;
        String sessionId = "session123";
        Livro livro = new Livro();
        LivroResponseDTO dto = new LivroResponseDTO();
        when(livroService.buscarPorId(id)).thenReturn(Optional.of(livro));
        when(livroMapper.toResponseDTO(livro)).thenReturn(dto);

        ResponseEntity<LivroResponseDTO> response = livroController.getBookById(id, sessionId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(recentViewService).registerView(sessionId, livro);
    }

    @Test
    void getBookById_DeveRetornarNotFound_QuandoNaoExiste() {
        Long id = 1L;
        String sessionId = "session123";
        when(livroService.buscarPorId(id)).thenReturn(Optional.empty());

        ResponseEntity<LivroResponseDTO> response = livroController.getBookById(id, sessionId);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void getBooksByGenre_DeveRetornarListaDeLivros() {
        String genre = "Ficção";
        List<Livro> livros = Collections.singletonList(new Livro());
        when(livroService.buscarPorGenero(genre)).thenReturn(livros);
        when(livroMapper.toResponseDTO(any(Livro.class))).thenReturn(new LivroResponseDTO());

        ResponseEntity<List<LivroResponseDTO>> response = livroController.getBooksByGenre(genre);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void getBooksByAuthor_DeveRetornarListaDeLivros() {
        String author = "Autor Teste";
        List<Livro> livros = Collections.singletonList(new Livro());
        when(livroService.buscarPorAutor(author)).thenReturn(livros);
        when(livroMapper.toResponseDTO(any(Livro.class))).thenReturn(new LivroResponseDTO());

        ResponseEntity<List<LivroResponseDTO>> response = livroController.getBooksByAuthor(author);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void createBook_DeveCriarLivro() {
        Livro livro = new Livro();
        LivroResponseDTO dto = new LivroResponseDTO();
        when(livroService.criar(livro)).thenReturn(livro);
        when(livroMapper.toResponseDTO(livro)).thenReturn(dto);

        ResponseEntity<LivroResponseDTO> response = livroController.createBook(livro);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(livroService).criar(livro);
    }

    @Test
    void updateBook_DeveAtualizarLivro_QuandoExiste() {
        Long id = 1L;
        Livro livro = new Livro();
        LivroResponseDTO dto = new LivroResponseDTO();
        when(livroService.atualizar(id, livro)).thenReturn(Optional.of(livro));
        when(livroMapper.toResponseDTO(livro)).thenReturn(dto);

        ResponseEntity<LivroResponseDTO> response = livroController.updateBook(id, livro);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void updateBook_DeveRetornarNotFound_QuandoNaoExiste() {
        Long id = 1L;
        Livro livro = new Livro();
        when(livroService.atualizar(id, livro)).thenReturn(Optional.empty());

        ResponseEntity<LivroResponseDTO> response = livroController.updateBook(id, livro);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void deleteBook_DeveDeletarLivro_QuandoExiste() {
        Long id = 1L;
        when(livroService.deletar(id)).thenReturn(true);

        ResponseEntity<Void> response = livroController.deleteBook(id);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    void deleteBook_DeveRetornarNotFound_QuandoNaoExiste() {
        Long id = 1L;
        when(livroService.deletar(id)).thenReturn(false);

        ResponseEntity<Void> response = livroController.deleteBook(id);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void getRecentBooks_DeveRetornarLivrosRecentes() {
        String sessionId = "session123";
        List<com.desafio.catalogodosabio.model.RecentView> views = Collections.singletonList(
            new com.desafio.catalogodosabio.model.RecentView(sessionId, new Livro(), java.time.LocalDateTime.now())
        );
        when(recentViewService.getRecentViews(sessionId)).thenReturn(views);
        when(livroMapper.toResponseDTO(any(Livro.class))).thenReturn(new LivroResponseDTO());

        ResponseEntity<List<LivroResponseDTO>> response = livroController.getRecentBooks(sessionId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }
} 