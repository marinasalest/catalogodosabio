package com.desafio.catalogodosabio.service;

import com.desafio.catalogodosabio.model.Livro;
import com.desafio.catalogodosabio.model.RecentView;
import com.desafio.catalogodosabio.repository.RecentViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecentViewService {

    private final RecentViewRepository recentViewRepository;

    @Autowired
    public RecentViewService(RecentViewRepository recentViewRepository) {
        this.recentViewRepository = recentViewRepository;
    }

    public void registerView(String sessionId, Livro livro) {
        RecentView view = new RecentView(sessionId, livro, LocalDateTime.now());
        recentViewRepository.save(view);
    }

    public List<RecentView> getRecentViews(String sessionId) {
        return recentViewRepository.findTop10BySessionIdOrderByViewedAtDesc(sessionId);
    }
}