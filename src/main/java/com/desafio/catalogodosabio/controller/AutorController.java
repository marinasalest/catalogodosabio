package com.desafio.catalogodosabio.controller;

import com.desafio.catalogodosabio.dto.AutorResponseDTO;
import com.desafio.catalogodosabio.mapper.AutorMapper;
import com.desafio.catalogodosabio.model.Autor;
import com.desafio.catalogodosabio.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AutorController {
    private final AutorService autorService;
    private final AutorMapper autorMapper;

    @Autowired
    public AutorController(AutorService autorService, AutorMapper autorMapper) {
        this.autorService = autorService;
        this.autorMapper = autorMapper;
    }

    @GetMapping
    public ResponseEntity<List<AutorResponseDTO>> getAllAuthors() {
        List<AutorResponseDTO> dtos = autorService.listarTodos()
                .stream()
                .map(autorMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> getAuthorById(@PathVariable Long id) {
        return autorService.buscarPorId(id)
                .map(autorMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AutorResponseDTO> createAuthor(@RequestBody Autor autor) {
        return ResponseEntity.ok(autorMapper.toResponseDTO(autorService.criar(autor)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> updateAuthor(@PathVariable Long id, @RequestBody Autor autor) {
        return autorService.atualizar(id, autor)
                .map(autorMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        if (autorService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
