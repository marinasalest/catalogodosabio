package com.desafio.catalogodosabio.controller;

import com.desafio.catalogodosabio.dto.LivroResponseDTO;
import com.desafio.catalogodosabio.mapper.LivroMapper;
import com.desafio.catalogodosabio.model.Livro;
import com.desafio.catalogodosabio.service.LivroService;
import com.desafio.catalogodosabio.service.RecentViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
public class LivroController {
    private final LivroService livroService;
    private final LivroMapper livroMapper;
    private final RecentViewService recentViewService;

    @Autowired
    public LivroController(LivroService livroService, LivroMapper livroMapper, RecentViewService recentViewService) {
        this.livroService = livroService;
        this.livroMapper = livroMapper;
        this.recentViewService = recentViewService;
    }

    @GetMapping
    public ResponseEntity<Page<LivroResponseDTO>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Livro> livrosPage = livroService.listarTodosPaginado(pageable);
        Page<LivroResponseDTO> dtoPage = livrosPage.map(livroMapper::toResponseDTO);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> getBookById(
            @PathVariable Long id,
            @RequestHeader(value = "X-Session-Id") String sessionId
    ) {
        return livroService.buscarPorId(id)
                .map(livro -> {
                    recentViewService.registerView(sessionId, livro);
                    return ResponseEntity.ok(livroMapper.toResponseDTO(livro));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<LivroResponseDTO>> getBooksByGenre(@PathVariable String genre) {
        List<LivroResponseDTO> dtos = livroService.buscarPorGenero(genre)
                .stream()
                .map(livroMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<LivroResponseDTO>> getBooksByAuthor(@PathVariable String author) {
        List<LivroResponseDTO> dtos = livroService.buscarPorAutor(author)
                .stream()
                .map(livroMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<LivroResponseDTO> createBook(@RequestBody Livro livro) {
        Livro novoLivro = livroService.criar(livro);
        return ResponseEntity.ok(livroMapper.toResponseDTO(novoLivro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> updateBook(@PathVariable Long id, @RequestBody Livro livro) {
        return livroService.atualizar(id, livro)
                .map(livroMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (livroService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/recent")
    public ResponseEntity<List<LivroResponseDTO>> getRecentBooks(
            @RequestHeader(value = "X-Session-Id") String sessionId
    ) {
        List<com.desafio.catalogodosabio.model.RecentView> views = recentViewService.getRecentViews(sessionId);
        List<LivroResponseDTO> dtos = views.stream()
                .map(view -> livroMapper.toResponseDTO(view.getLivro()))
                .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
