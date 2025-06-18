package com.desafio.catalogodosabio.mapper;

import com.desafio.catalogodosabio.dto.TipoResponseDTO;
import com.desafio.catalogodosabio.model.Tipo;
import org.springframework.stereotype.Component;

@Component
public class TipoMapper {

    public TipoResponseDTO toResponseDTO(Tipo tipo) {
        if (tipo == null) return null;
        TipoResponseDTO dto = new TipoResponseDTO();
        dto.setId(tipo.getId());
        dto.setNome(tipo.getNome());
        return dto;
    }
}