package com.desafio.catalogodosabio.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recent_views")
public class RecentView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sessionId;
    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;
    private LocalDateTime viewedAt;
    public RecentView() {}
    public RecentView(String sessionId, Livro livro, LocalDateTime viewedAt) {
        this.sessionId = sessionId;
        this.livro = livro;
        this.viewedAt = viewedAt;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }
    public LocalDateTime getViewedAt() { return viewedAt; }
    public void setViewedAt(LocalDateTime viewedAt) { this.viewedAt = viewedAt; }
}