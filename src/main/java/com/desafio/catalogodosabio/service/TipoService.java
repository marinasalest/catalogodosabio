package com.desafio.catalogodosabio.service;

import com.desafio.catalogodosabio.model.Tipo;
import com.desafio.catalogodosabio.repository.TipoRepository;
import com.desafio.catalogodosabio.exception.ResourceNotFoundException;
import com.desafio.catalogodosabio.exception.BadRequestException;
import com.desafio.catalogodosabio.exception.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoService {

    private final TipoRepository tipoRepository;

    @Autowired
    public TipoService(TipoRepository tipoRepository) {
        this.tipoRepository = tipoRepository;
    }

    public List<Tipo> listarTodos() {
        return tipoRepository.findAll();
    }

    public Optional<Tipo> buscarPorId(Long id) {
        return tipoRepository.findById(id);
    }

    public Tipo buscarPorIdOuFalhar(Long id) {
        return tipoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tipo não encontrado com id: " + id));
    }

    public Tipo criar(Tipo tipo) {
        if (tipo.getNome() == null || tipo.getNome().isEmpty()) {
            throw new BadRequestException("Nome do tipo é obrigatório.");
        }
        try {
            return tipoRepository.save(tipo);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Violação de integridade ao salvar tipo.");
        }
    }

    public Optional<Tipo> atualizar(Long id, Tipo tipoAtualizado) {
        return tipoRepository.findById(id).map(tipo -> {
            tipo.setNome(tipoAtualizado.getNome());
            return tipoRepository.save(tipo);
        });
    }

    public boolean deletar(Long id) {
        if (tipoRepository.existsById(id)) {
            tipoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}