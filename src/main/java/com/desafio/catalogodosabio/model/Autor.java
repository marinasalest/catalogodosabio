package com.desafio.catalogodosabio.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(length = 100)
    private String nacionalidade;

    @Column(name = "qtd_livros_escritos", nullable = false)
    private Integer qtdLivrosEscritos = 0;

    @Column(length = 1000)
    private String biografia;

    public Autor() {}

    public Autor(String nome, String nacionalidade, Integer qtdLivrosEscritos) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
        this.qtdLivrosEscritos = qtdLivrosEscritos;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }
    public Integer getQtdLivrosEscritos() { return qtdLivrosEscritos; }
    public void setQtdLivrosEscritos(Integer qtdLivrosEscritos) { this.qtdLivrosEscritos = qtdLivrosEscritos; }
    public String getBiografia() { return biografia; }
    public void setBiografia(String biografia) { this.biografia = biografia; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autor autor = (Autor) o;
        return Objects.equals(id, autor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setId(Long id) {
        this.id = id;
    }
}