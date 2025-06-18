package com.desafio.catalogodosabio.mapper;

import com.desafio.catalogodosabio.dto.LivroResponseDTO;
import com.desafio.catalogodosabio.model.Livro;
import org.springframework.stereotype.Component;

@Component
public class LivroMapper {

    public LivroResponseDTO toResponseDTO(Livro livro) {
        if (livro == null) return null;
        LivroResponseDTO dto = new LivroResponseDTO();
        dto.setId(livro.getId());
        dto.setTitulo(livro.getTitulo());
        dto.setNomeAutor(livro.getAutor() != null ? livro.getAutor().getNome() : null);
        dto.setCapaUrl(livro.getCapaUrl());
        dto.setAno(livro.getAno());
        dto.setSinopse(livro.getSinopse());
        dto.setNomeGenero(livro.getGenero() != null ? livro.getGenero().getNome() : null);
        dto.setNomeEditora(livro.getEditora() != null ? livro.getEditora().getNome() : null);
        dto.setNomeTipo(livro.getTipo() != null ? livro.getTipo().getNome() : null);
        dto.setNomeIdioma(livro.getIdioma() != null ? livro.getIdioma().getNome() : null);
        dto.setPreco(livro.getPreco());
        dto.setQtd(livro.getQtd());
        return dto;
    }
}