package com.desafio.catalogodosabio.service;

import com.desafio.catalogodosabio.model.Livro;
import com.desafio.catalogodosabio.repository.LivroRepository;
import com.desafio.catalogodosabio.exception.ResourceNotFoundException;
import com.desafio.catalogodosabio.exception.BadRequestException;
import com.desafio.catalogodosabio.exception.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    @Autowired
    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Optional<Livro> buscarPorId(Long id) {
        return livroRepository.findById(id);
    }

    public Livro buscarPorIdOuFalhar(Long id) {
        return livroRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com id: " + id));
    }

    public Livro criar(Livro livro) {
        if (livro.getTitulo() == null || livro.getTitulo().isEmpty()) {
            throw new BadRequestException("Título do livro é obrigatório.");
        }
        try {
            return livroRepository.save(livro);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Violação de integridade ao salvar livro.");
        }
    }

    public Optional<Livro> atualizar(Long id, Livro livroAtualizado) {
        return livroRepository.findById(id).map(livro -> {
            livro.setTitulo(livroAtualizado.getTitulo());
            livro.setAutor(livroAtualizado.getAutor());
            livro.setCapaUrl(livroAtualizado.getCapaUrl());
            livro.setAno(livroAtualizado.getAno());
            livro.setSinopse(livroAtualizado.getSinopse());
            livro.setGenero(livroAtualizado.getGenero());
            livro.setEditora(livroAtualizado.getEditora());
            livro.setTipo(livroAtualizado.getTipo());
            livro.setIdioma(livroAtualizado.getIdioma());
            livro.setPreco(livroAtualizado.getPreco());
            livro.setQtd(livroAtualizado.getQtd());
            return livroRepository.save(livro);
        });
    }

    public boolean deletar(Long id) {
        if (livroRepository.existsById(id)) {
            livroRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Page<Livro> listarTodosPaginado(Pageable pageable) {
        return livroRepository.findAll(pageable);
    }

    public List<Livro> buscarPorGenero(String genero) {
        return livroRepository.findByGeneroNomeIgnoreCase(genero);
    }

    public List<Livro> buscarPorAutor(String autor) {
        return livroRepository.findByAutorNomeIgnoreCase(autor);
    }
}