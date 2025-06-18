package com.desafio.catalogodosabio.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @Column(name = "capa_url", length = 1024)
    private String capaUrl;

    private Integer ano;

    @Column(columnDefinition = "TEXT")
    private String sinopse;

    @ManyToOne
    @JoinColumn(name = "genero_id", nullable = false)
    private Genero genero;

    @ManyToOne
    @JoinColumn(name = "editora_id", nullable = false)
    private Editora editora;

    @ManyToOne
    @JoinColumn(name = "tipo_id", nullable = false)
    private Tipo tipo;

    @ManyToOne
    @JoinColumn(name = "idioma_id", nullable = false)
    private Idioma idioma;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    private Integer qtd;

    @Column(length = 13)
    private String isbn;

    public Livro() {}

    public Livro(String titulo, Autor autor, String capaUrl, Integer ano, String sinopse,
                 Genero genero, Editora editora, Tipo tipo, Idioma idioma,
                 BigDecimal preco, Integer qtd) {
        this.titulo = titulo;
        this.autor = autor;
        this.capaUrl = capaUrl;
        this.ano = ano;
        this.sinopse = sinopse;
        this.genero = genero;
        this.editora = editora;
        this.tipo = tipo;
        this.idioma = idioma;
        this.preco = preco;
        this.qtd = qtd;
    }


    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
    public String getCapaUrl() { return capaUrl; }
    public void setCapaUrl(String capaUrl) { this.capaUrl = capaUrl; }
    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
    public String getSinopse() { return sinopse; }
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }
    public Genero getGenero() { return genero; }
    public void setGenero(Genero genero) { this.genero = genero; }
    public Editora getEditora() { return editora; }
    public void setEditora(Editora editora) { this.editora = editora; }
    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }
    public Idioma getIdioma() { return idioma; }
    public void setIdioma(Idioma idioma) { this.idioma = idioma; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public Integer getQtd() { return qtd; }
    public void setQtd(Integer qtd) { this.qtd = qtd; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(id, livro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setId(Long id) {
        this.id = id;
    }
}