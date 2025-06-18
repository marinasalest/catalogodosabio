package com.desafio.catalogodosabio.mapper;

import com.desafio.catalogodosabio.dto.AutorResponseDTO;
import com.desafio.catalogodosabio.model.Autor;
import org.springframework.stereotype.Component;

@Component
public class AutorMapper {

    public AutorResponseDTO toResponseDTO(Autor autor) {
        if (autor == null) return null;
        AutorResponseDTO dto = new AutorResponseDTO();
        dto.setId(autor.getId());
        dto.setNome(autor.getNome());
        dto.setNacionalidade(autor.getNacionalidade());
        dto.setQtdLivrosEscritos(autor.getQtdLivrosEscritos());
        return dto;
    }
}