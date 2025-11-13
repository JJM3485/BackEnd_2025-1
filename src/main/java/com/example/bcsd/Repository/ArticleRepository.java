package com.example.bcsd.Repository;

import com.example.bcsd.DTO.Article; // DTO 폴더에 있는 ArticleEntity를 import
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ArticleRepository {

    private final Map<Long, Article> articles = new HashMap<>();
    private final AtomicLong idCount = new AtomicLong();

    public Article save(Article article) {
        if (article.getId() == null) {
            long newId = idCount.incrementAndGet();
            article = new Article(newId, article.getDescription());
        }
        articles.put(article.getId(), article);
        return article;
    }

    public Article findById(Long id) {
        return articles.get(id);
    }

    public Article deleteById(Long id) {
        return articles.remove(id);
    }
}