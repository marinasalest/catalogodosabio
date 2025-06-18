package com.desafio.catalogodosabio.mapper;

import com.desafio.catalogodosabio.dto.IdiomaResponseDTO;
import com.desafio.catalogodosabio.model.Idioma;
import org.springframework.stereotype.Component;

@Component
public class IdiomaMapper {

    public IdiomaResponseDTO toResponseDTO(Idioma idioma) {
        if (idioma == null) return null;
        IdiomaResponseDTO dto = new IdiomaResponseDTO();
        dto.setId(idioma.getId());
        dto.setNome(idioma.getNome());
        return dto;
    }
}