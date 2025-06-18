package com.desafio.catalogodosabio.service;

import com.desafio.catalogodosabio.model.Livro;
import com.desafio.catalogodosabio.model.RecentView;
import com.desafio.catalogodosabio.repository.RecentViewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecentViewServiceTest {
    @Mock
    private RecentViewRepository recentViewRepository;
    @InjectMocks
    private RecentViewService recentViewService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void registerView_DeveSalvarRecentView() {
        Livro livro = new Livro();
        String sessionId = "sessao123";
        recentViewService.registerView(sessionId, livro);
        verify(recentViewRepository).save(any(RecentView.class));
    }
    @Test
    void getRecentViews_DeveRetornarLista() {
        String sessionId = "sessao123";
        when(recentViewRepository.findTop10BySessionIdOrderByViewedAtDesc(sessionId)).thenReturn(Collections.emptyList());
        List<RecentView> result = recentViewService.getRecentViews(sessionId);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}