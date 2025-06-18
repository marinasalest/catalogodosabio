package com.desafio.catalogodosabio.mapper;

import com.desafio.catalogodosabio.dto.GeneroResponseDTO;
import com.desafio.catalogodosabio.model.Genero;
import org.springframework.stereotype.Component;

@Component
public class GeneroMapper {

    public GeneroResponseDTO toResponseDTO(Genero genero) {
        if (genero == null) return null;
        GeneroResponseDTO dto = new GeneroResponseDTO();
        dto.setId(genero.getId());
        dto.setNome(genero.getNome());
        return dto;
    }
}