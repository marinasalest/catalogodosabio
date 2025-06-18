package com.desafio.catalogodosabio.integration;

import com.desafio.catalogodosabio.dto.AutorResponseDTO;
import com.desafio.catalogodosabio.model.Autor;
import com.desafio.catalogodosabio.repository.AutorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AutorIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AutorRepository autorRepository;

    @Test
    void criarAutor_DeveRetornarAutorCriado() throws Exception {
        Autor autor = new Autor();
        autor.setNome("Autor Teste");
        autor.setBiografia("Biografia do autor teste");

        MvcResult result = mockMvc.perform(post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(autor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Autor Teste"))
                .andReturn();

        AutorResponseDTO response = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            AutorResponseDTO.class
        );

        assertNotNull(response.getId());
        assertEquals("Autor Teste", response.getNome());
    }

    @Test
    void buscarAutorPorId_DeveRetornarAutor_QuandoExiste() throws Exception {
        Autor autor = new Autor();
        autor.setNome("Autor Teste");
        autor.setBiografia("Biografia do autor teste");
        autor = autorRepository.save(autor);

        mockMvc.perform(get("/authors/{id}", autor.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(autor.getId()))
                .andExpect(jsonPath("$.nome").value("Autor Teste"));
    }

    @Test
    void buscarAutorPorId_DeveRetornarNotFound_QuandoNaoExiste() throws Exception {
        mockMvc.perform(get("/authors/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void atualizarAutor_DeveAtualizarAutor_QuandoExiste() throws Exception {
        Autor autor = new Autor();
        autor.setNome("Autor Original");
        autor.setBiografia("Biografia original");
        autor = autorRepository.save(autor);

        autor.setNome("Autor Atualizado");

        mockMvc.perform(put("/authors/{id}", autor.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(autor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Autor Atualizado"));
    }

    @Test
    void deletarAutor_DeveDeletarAutor_QuandoExiste() throws Exception {
        Autor autor = new Autor();
        autor.setNome("Autor para Deletar");
        autor.setBiografia("Biografia do autor para deletar");
        autor = autorRepository.save(autor);

        mockMvc.perform(delete("/authors/{id}", autor.getId()))
                .andExpect(status().isNoContent());

        assertFalse(autorRepository.existsById(autor.getId()));
    }

    @Test
    void listarAutores_DeveRetornarListaDeAutores() throws Exception {
        Autor autor1 = new Autor();
        autor1.setNome("Autor 1");
        autor1.setBiografia("Biografia 1");
        autorRepository.save(autor1);

        Autor autor2 = new Autor();
        autor2.setNome("Autor 2");
        autor2.setBiografia("Biografia 2");
        autorRepository.save(autor2);

        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nome").exists())
                .andExpect(jsonPath("$[1].nome").exists());
    }
} 