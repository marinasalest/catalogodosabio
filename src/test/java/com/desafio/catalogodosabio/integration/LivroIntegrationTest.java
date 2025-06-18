package com.desafio.catalogodosabio.integration;

import com.desafio.catalogodosabio.dto.LivroResponseDTO;
import com.desafio.catalogodosabio.model.Autor;
import com.desafio.catalogodosabio.model.Editora;
import com.desafio.catalogodosabio.model.Genero;
import com.desafio.catalogodosabio.model.Idioma;
import com.desafio.catalogodosabio.model.Livro;
import com.desafio.catalogodosabio.model.Tipo;
import com.desafio.catalogodosabio.repository.AutorRepository;
import com.desafio.catalogodosabio.repository.EditoraRepository;
import com.desafio.catalogodosabio.repository.GeneroRepository;
import com.desafio.catalogodosabio.repository.IdiomaRepository;
import com.desafio.catalogodosabio.repository.LivroRepository;
import com.desafio.catalogodosabio.repository.TipoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.hamcrest.Matchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LivroIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private EditoraRepository editoraRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @Autowired
    private IdiomaRepository idiomaRepository;

    @Autowired
    private TipoRepository tipoRepository;

    private Autor criarAutor() {
        Autor autor = new Autor();
        autor.setNome("Autor Teste");
        autor.setNacionalidade("Brasileiro");
        autor.setQtdLivrosEscritos(1);
        return autorRepository.save(autor);
    }

    private Editora criarEditora() {
        Editora editora = new Editora();
        editora.setNome("Editora Teste");
        editora.setCnpj("12345678000199");
        editora.setEmail("editora@teste.com");
        editora.setTelefone("11999999999");
        return editoraRepository.save(editora);
    }

    private Genero criarGenero() {
        Genero genero = new Genero();
        genero.setNome("Ficção");
        return generoRepository.save(genero);
    }

    private Tipo criarTipo() {
        Tipo tipo = new Tipo();
        tipo.setNome("Impresso");
        return tipoRepository.save(tipo);
    }

    private Idioma criarIdioma() {
        Idioma idioma = new Idioma();
        idioma.setNome("Português");
        return idiomaRepository.save(idioma);
    }

    @Test
    void criarLivro_DeveRetornarLivroCriado() throws Exception {
        Autor autor = criarAutor();
        Editora editora = criarEditora();
        Genero genero = criarGenero();
        Tipo tipo = criarTipo();
        Idioma idioma = criarIdioma();
        Livro livro = new Livro();
        livro.setTitulo("Livro Teste");
        livro.setAutor(autor);
        livro.setEditora(editora);
        livro.setGenero(genero);
        livro.setTipo(tipo);
        livro.setIdioma(idioma);
        livro.setPreco(new java.math.BigDecimal("49.90"));
        livro.setQtd(10);
        livro.setIsbn("1234567890123");

        MvcResult result = mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(livro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Livro Teste"))
                .andReturn();

        LivroResponseDTO response = objectMapper.readValue(
            result.getResponse().getContentAsString(),
            LivroResponseDTO.class
        );

        assertNotNull(response.getId());
        assertEquals("Livro Teste", response.getTitulo());
    }

    @Test
    void buscarLivroPorId_DeveRetornarLivro_QuandoExiste() throws Exception {
        Autor autor = criarAutor();
        Editora editora = criarEditora();
        Genero genero = criarGenero();
        Tipo tipo = criarTipo();
        Idioma idioma = criarIdioma();
        Livro livro = new Livro();
        livro.setTitulo("Livro Teste");
        livro.setAutor(autor);
        livro.setEditora(editora);
        livro.setGenero(genero);
        livro.setTipo(tipo);
        livro.setIdioma(idioma);
        livro.setPreco(new java.math.BigDecimal("49.90"));
        livro.setQtd(10);
        livro.setIsbn("1234567890123");
        livro = livroRepository.save(livro);

        mockMvc.perform(get("/books/{id}", livro.getId())
                .header("X-Session-Id", "session123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(livro.getId()))
                .andExpect(jsonPath("$.titulo").value("Livro Teste"));
    }

    @Test
    void buscarLivroPorId_DeveRetornarNotFound_QuandoNaoExiste() throws Exception {
        mockMvc.perform(get("/books/{id}", Long.MAX_VALUE)
                .header("X-Session-Id", "session123"))
                .andExpect(status().isNotFound());
    }

    @Test
    void atualizarLivro_DeveAtualizarLivro_QuandoExiste() throws Exception {
        Autor autor = criarAutor();
        Editora editora = criarEditora();
        Genero genero = criarGenero();
        Tipo tipo = criarTipo();
        Idioma idioma = criarIdioma();
        Livro livro = new Livro();
        livro.setTitulo("Livro Original");
        livro.setAutor(autor);
        livro.setEditora(editora);
        livro.setGenero(genero);
        livro.setTipo(tipo);
        livro.setIdioma(idioma);
        livro.setPreco(new java.math.BigDecimal("49.90"));
        livro.setQtd(10);
        livro.setIsbn("1234567890123");
        livro = livroRepository.save(livro);

        livro.setTitulo("Livro Atualizado");

        mockMvc.perform(put("/books/{id}", livro.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(livro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Livro Atualizado"));
    }

    @Test
    void deletarLivro_DeveDeletarLivro_QuandoExiste() throws Exception {
        Autor autor = criarAutor();
        Editora editora = criarEditora();
        Genero genero = criarGenero();
        Tipo tipo = criarTipo();
        Idioma idioma = criarIdioma();
        Livro livro = new Livro();
        livro.setTitulo("Livro para Deletar");
        livro.setAutor(autor);
        livro.setEditora(editora);
        livro.setGenero(genero);
        livro.setTipo(tipo);
        livro.setIdioma(idioma);
        livro.setPreco(new java.math.BigDecimal("49.90"));
        livro.setQtd(10);
        livro.setIsbn("1234567890123");
        livro = livroRepository.save(livro);

        mockMvc.perform(delete("/books/{id}", livro.getId()))
                .andExpect(status().isNoContent());

        assertFalse(livroRepository.existsById(livro.getId()));
    }

    @Test
    void listarLivros_DeveRetornarPaginaDeLivros() throws Exception {
        Autor autor = criarAutor();
        Editora editora = criarEditora();
        Genero genero = criarGenero();
        Tipo tipo = criarTipo();
        Idioma idioma = criarIdioma();
        Livro livro1 = new Livro();
        livro1.setTitulo("Livro 1");
        livro1.setAutor(autor);
        livro1.setEditora(editora);
        livro1.setGenero(genero);
        livro1.setTipo(tipo);
        livro1.setIdioma(idioma);
        livro1.setPreco(new java.math.BigDecimal("49.90"));
        livro1.setQtd(10);
        livro1.setIsbn("1234567890123");
        livroRepository.save(livro1);

        Livro livro2 = new Livro();
        livro2.setTitulo("Livro 2");
        livro2.setAutor(autor);
        livro2.setEditora(editora);
        livro2.setGenero(genero);
        livro2.setTipo(tipo);
        livro2.setIdioma(idioma);
        livro2.setPreco(new java.math.BigDecimal("59.90"));
        livro2.setQtd(5);
        livro2.setIsbn("9876543210123");
        livroRepository.save(livro2);

        mockMvc.perform(get("/books")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements", Matchers.greaterThanOrEqualTo(2)));
    }
} 