package com.desafio.catalogodosabio.mapper;

import com.desafio.catalogodosabio.dto.EditoraResponseDTO;
import com.desafio.catalogodosabio.model.Editora;
import org.springframework.stereotype.Component;

@Component
public class EditoraMapper {

    public EditoraResponseDTO toResponseDTO(Editora editora) {
        if (editora == null) return null;
        EditoraResponseDTO dto = new EditoraResponseDTO();
        dto.setId(editora.getId());
        dto.setNome(editora.getNome());
        dto.setCnpj(editora.getCnpj());
        dto.setEmail(editora.getEmail());
        dto.setTelefone(editora.getTelefone());
        return dto;
    }
}