package com.rag.ragApp.repository;

import com.rag.ragApp.entity.LawBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LawBookRepository extends JpaRepository<LawBookEntity, Long> {
}
