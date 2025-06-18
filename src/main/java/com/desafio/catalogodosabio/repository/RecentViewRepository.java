package com.desafio.catalogodosabio.repository;

import com.desafio.catalogodosabio.model.RecentView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface RecentViewRepository extends JpaRepository<RecentView, Long> {
    List<RecentView> findTop10BySessionIdOrderByViewedAtDesc(String sessionId);
}