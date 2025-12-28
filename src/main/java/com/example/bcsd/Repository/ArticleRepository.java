package com.example.bcsd.Repository;

import com.example.bcsd.DTO.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByBoardId(Long boardId);
}

