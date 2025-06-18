package com.desafio.catalogodosabio.service;

import com.desafio.catalogodosabio.model.Idioma;
import com.desafio.catalogodosabio.repository.IdiomaRepository;
import com.desafio.catalogodosabio.exception.ResourceNotFoundException;
import com.desafio.catalogodosabio.exception.BadRequestException;
import com.desafio.catalogodosabio.exception.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdiomaService {

    private final IdiomaRepository idiomaRepository;

    @Autowired
    public IdiomaService(IdiomaRepository idiomaRepository) {
        this.idiomaRepository = idiomaRepository;
    }

    public List<Idioma> listarTodos() {
        return idiomaRepository.findAll();
    }

    public Idioma buscarPorIdOuFalhar(Long id) {
        return idiomaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Idioma não encontrado com id: " + id));
    }

    public Idioma criar(Idioma idioma) {
        if (idioma.getNome() == null || idioma.getNome().isEmpty()) {
            throw new BadRequestException("Nome do idioma é obrigatório.");
        }
        try {
            return idiomaRepository.save(idioma);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Violação de integridade ao salvar idioma.");
        }
    }

    public Optional<Idioma> atualizar(Long id, Idioma idiomaAtualizado) {
        return idiomaRepository.findById(id).map(idioma -> {
            idioma.setNome(idiomaAtualizado.getNome());
            return idiomaRepository.save(idioma);
        });
    }

    public boolean deletar(Long id) {
        if (idiomaRepository.existsById(id)) {
            idiomaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Idioma> buscarPorId(Long id) {
        return idiomaRepository.findById(id);
    }
}