package com.desafio.catalogodosabio.controller;

import com.desafio.catalogodosabio.dto.GeneroResponseDTO;
import com.desafio.catalogodosabio.mapper.GeneroMapper;
import com.desafio.catalogodosabio.model.Genero;
import com.desafio.catalogodosabio.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/genres")
public class GeneroController {
    private final GeneroService generoService;
    private final GeneroMapper generoMapper;

    @Autowired
    public GeneroController(GeneroService generoService, GeneroMapper generoMapper) {
        this.generoService = generoService;
        this.generoMapper = generoMapper;
    }

    @GetMapping
    public ResponseEntity<List<GeneroResponseDTO>> getAllGenres() {
        List<GeneroResponseDTO> dtos = generoService.listarTodos()
                .stream()
                .map(generoMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneroResponseDTO> getGenreById(@PathVariable Long id) {
        return generoService.buscarPorId(id)
                .map(generoMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GeneroResponseDTO> createGenre(@RequestBody Genero genero) {
        return ResponseEntity.ok(generoMapper.toResponseDTO(generoService.criar(genero)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneroResponseDTO> updateGenre(@PathVariable Long id, @RequestBody Genero genero) {
        return generoService.atualizar(id, genero)
                .map(generoMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        if (generoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 