package com.desafio.catalogodosabio.controller;

import com.desafio.catalogodosabio.dto.EditoraResponseDTO;
import com.desafio.catalogodosabio.mapper.EditoraMapper;
import com.desafio.catalogodosabio.model.Editora;
import com.desafio.catalogodosabio.service.EditoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/publishers")
public class EditoraController {
    private final EditoraService editoraService;
    private final EditoraMapper editoraMapper;

    @Autowired
    public EditoraController(EditoraService editoraService, EditoraMapper editoraMapper) {
        this.editoraService = editoraService;
        this.editoraMapper = editoraMapper;
    }

    @GetMapping
    public ResponseEntity<List<EditoraResponseDTO>> getAllPublishers() {
        List<EditoraResponseDTO> dtos = editoraService.listarTodos()
                .stream()
                .map(editoraMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditoraResponseDTO> getPublisherById(@PathVariable Long id) {
        return editoraService.buscarPorId(id)
                .map(editoraMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EditoraResponseDTO> createPublisher(@RequestBody Editora editora) {
        return ResponseEntity.ok(editoraMapper.toResponseDTO(editoraService.criar(editora)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditoraResponseDTO> updatePublisher(@PathVariable Long id, @RequestBody Editora editora) {
        return editoraService.atualizar(id, editora)
                .map(editoraMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long id) {
        if (editoraService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
