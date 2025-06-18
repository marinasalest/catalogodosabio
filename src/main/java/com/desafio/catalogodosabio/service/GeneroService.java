package com.desafio.catalogodosabio.service;

import com.desafio.catalogodosabio.model.Genero;
import com.desafio.catalogodosabio.repository.GeneroRepository;
import com.desafio.catalogodosabio.exception.ResourceNotFoundException;
import com.desafio.catalogodosabio.exception.BadRequestException;
import com.desafio.catalogodosabio.exception.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroService {

    private final GeneroRepository generoRepository;

    @Autowired
    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    public List<Genero> listarTodos() {
        return generoRepository.findAll();
    }

    public Optional<Genero> buscarPorId(Long id) {
        return generoRepository.findById(id);
    }

    public Genero buscarPorIdOuFalhar(Long id) {
        return generoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Gênero não encontrado com id: " + id));
    }

    public Genero criar(Genero genero) {
        if (genero.getNome() == null || genero.getNome().isEmpty()) {
            throw new BadRequestException("Nome do gênero é obrigatório.");
        }
        try {
            return generoRepository.save(genero);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Violação de integridade ao salvar gênero.");
        }
    }

    public Optional<Genero> atualizar(Long id, Genero generoAtualizado) {
        return generoRepository.findById(id).map(genero -> {
            genero.setNome(generoAtualizado.getNome());
            return generoRepository.save(genero);
        });
    }

    public boolean deletar(Long id) {
        if (generoRepository.existsById(id)) {
            generoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}