package com.desafio.catalogodosabio.dto;

public class AutorResponseDTO {
    private Long id;
    private String nome;
    private String nacionalidade;
    private Integer qtdLivrosEscritos;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }

    public Integer getQtdLivrosEscritos() { return qtdLivrosEscritos; }
    public void setQtdLivrosEscritos(Integer qtdLivrosEscritos) { this.qtdLivrosEscritos = qtdLivrosEscritos; }
}