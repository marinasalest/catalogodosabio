package com.desafio.catalogodosabio.controller;

import com.desafio.catalogodosabio.dto.IdiomaResponseDTO;
import com.desafio.catalogodosabio.mapper.IdiomaMapper;
import com.desafio.catalogodosabio.model.Idioma;
import com.desafio.catalogodosabio.service.IdiomaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/languages")
public class IdiomaController {
    private final IdiomaService idiomaService;
    private final IdiomaMapper idiomaMapper;

    @Autowired
    public IdiomaController(IdiomaService idiomaService, IdiomaMapper idiomaMapper) {
        this.idiomaService = idiomaService;
        this.idiomaMapper = idiomaMapper;
    }

    @GetMapping
    public ResponseEntity<List<IdiomaResponseDTO>> getAllLanguages() {
        List<IdiomaResponseDTO> dtos = idiomaService.listarTodos()
                .stream()
                .map(idiomaMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IdiomaResponseDTO> getLanguageById(@PathVariable Long id) {
        return idiomaService.buscarPorId(id)
                .map(idiomaMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<IdiomaResponseDTO> createLanguage(@RequestBody Idioma idioma) {
        return ResponseEntity.ok(idiomaMapper.toResponseDTO(idiomaService.criar(idioma)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IdiomaResponseDTO> updateLanguage(@PathVariable Long id, @RequestBody Idioma idioma) {
        return idiomaService.atualizar(id, idioma)
                .map(idiomaMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable Long id) {
        if (idiomaService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
