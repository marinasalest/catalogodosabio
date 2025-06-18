package com.desafio.catalogodosabio.dto;

import java.math.BigDecimal;

public class LivroResponseDTO {
    private Long id;
    private String titulo;
    private String nomeAutor;
    private String capaUrl;
    private Integer ano;
    private String sinopse;
    private String nomeGenero;
    private String nomeEditora;
    private String nomeTipo;
    private String nomeIdioma;
    private BigDecimal preco;
    private Integer qtd;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getNomeAutor() { return nomeAutor; }
    public void setNomeAutor(String nomeAutor) { this.nomeAutor = nomeAutor; }

    public String getCapaUrl() { return capaUrl; }
    public void setCapaUrl(String capaUrl) { this.capaUrl = capaUrl; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }

    public String getNomeGenero() { return nomeGenero; }
    public void setNomeGenero(String nomeGenero) { this.nomeGenero = nomeGenero; }

    public String getNomeEditora() { return nomeEditora; }
    public void setNomeEditora(String nomeEditora) { this.nomeEditora = nomeEditora; }

    public String getNomeTipo() { return nomeTipo; }
    public void setNomeTipo(String nomeTipo) { this.nomeTipo = nomeTipo; }

    public String getNomeIdioma() { return nomeIdioma; }
    public void setNomeIdioma(String nomeIdioma) { this.nomeIdioma = nomeIdioma; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public Integer getQtd() { return qtd; }
    public void setQtd(Integer qtd) { this.qtd = qtd; }
}