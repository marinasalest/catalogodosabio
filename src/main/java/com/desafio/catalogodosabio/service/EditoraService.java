package com.desafio.catalogodosabio.service;

import com.desafio.catalogodosabio.model.Editora;
import com.desafio.catalogodosabio.repository.EditoraRepository;
import com.desafio.catalogodosabio.exception.ResourceNotFoundException;
import com.desafio.catalogodosabio.exception.BadRequestException;
import com.desafio.catalogodosabio.exception.ConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditoraService {

    private final EditoraRepository editoraRepository;

    @Autowired
    public EditoraService(EditoraRepository editoraRepository) {
        this.editoraRepository = editoraRepository;
    }

    public List<Editora> listarTodos() {
        return editoraRepository.findAll();
    }

    public Editora buscarPorIdOuFalhar(Long id) {
        return editoraRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Editora não encontrada com id: " + id));
    }

    public Editora criar(Editora editora) {
        if (editora.getNome() == null || editora.getNome().isEmpty()) {
            throw new BadRequestException("Nome da editora é obrigatório.");
        }
        try {
            return editoraRepository.save(editora);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Violação de integridade ao salvar editora.");
        }
    }

    public Optional<Editora> atualizar(Long id, Editora editoraAtualizada) {
        return editoraRepository.findById(id).map(editora -> {
            editora.setNome(editoraAtualizada.getNome());
            editora.setCnpj(editoraAtualizada.getCnpj());
            editora.setEmail(editoraAtualizada.getEmail());
            editora.setTelefone(editoraAtualizada.getTelefone());
            return editoraRepository.save(editora);
        });
    }

    public boolean deletar(Long id) {
        if (editoraRepository.existsById(id)) {
            editoraRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Editora> buscarPorId(Long id) {
        return editoraRepository.findById(id);
    }
}