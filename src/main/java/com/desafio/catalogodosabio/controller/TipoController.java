package com.desafio.catalogodosabio.controller;

import com.desafio.catalogodosabio.dto.TipoResponseDTO;
import com.desafio.catalogodosabio.mapper.TipoMapper;
import com.desafio.catalogodosabio.model.Tipo;
import com.desafio.catalogodosabio.service.TipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/types")
public class TipoController {
    private final TipoService tipoService;
    private final TipoMapper tipoMapper;

    @Autowired
    public TipoController(TipoService tipoService, TipoMapper tipoMapper) {
        this.tipoService = tipoService;
        this.tipoMapper = tipoMapper;
    }

    @GetMapping
    public ResponseEntity<List<TipoResponseDTO>> getAllTypes() {
        List<TipoResponseDTO> dtos = tipoService.listarTodos()
                .stream()
                .map(tipoMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoResponseDTO> getTypeById(@PathVariable Long id) {
        return tipoService.buscarPorId(id)
                .map(tipoMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoResponseDTO> createType(@RequestBody Tipo tipo) {
        return ResponseEntity.ok(tipoMapper.toResponseDTO(tipoService.criar(tipo)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoResponseDTO> updateType(@PathVariable Long id, @RequestBody Tipo tipo) {
        return tipoService.atualizar(id, tipo)
                .map(tipoMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        if (tipoService.deletar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
