package com.desafio.catalogodosabio.service;

import com.desafio.catalogodosabio.model.Autor;
import com.desafio.catalogodosabio.repository.AutorRepository;
import com.desafio.catalogodosabio.exception.ResourceNotFoundException;
import com.desafio.catalogodosabio.exception.BadRequestException;
import com.desafio.catalogodosabio.exception.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    @Autowired
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Autor buscarPorIdOuFalhar(Long id) {
        return autorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado com id: " + id));
    }

    public Autor criar(Autor autor) {
        if (autor.getNome() == null || autor.getNome().isEmpty()) {
            throw new BadRequestException("Nome do autor é obrigatório.");
        }
        try {
            return autorRepository.save(autor);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Violação de integridade ao salvar autor.");
        }
    }

    public Optional<Autor> atualizar(Long id, Autor autorAtualizado) {
        return autorRepository.findById(id).map(autor -> {
            autor.setNome(autorAtualizado.getNome());
            autor.setNacionalidade(autorAtualizado.getNacionalidade());
            autor.setQtdLivrosEscritos(autorAtualizado.getQtdLivrosEscritos());
            return autorRepository.save(autor);
        });
    }

    public boolean deletar(Long id) {
        if (autorRepository.existsById(id)) {
            autorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Autor> buscarPorId(Long id) {
        return autorRepository.findById(id);
    }
}