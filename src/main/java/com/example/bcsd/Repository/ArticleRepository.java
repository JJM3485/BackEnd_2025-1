package com.example.bcsd.Repository;

import com.example.bcsd.DTO.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ArticleRepository {

    private final Map<Long, Article> articles = new HashMap<>();
    private final AtomicLong idCount = new AtomicLong();

    public Article save(Article article) {
        if (article.getId() == null) {
            long newId = idCount.incrementAndGet();
            article.setId(newId);
        }
        articles.put(article.getId(), article);
        return article;
    }

    public Article findById(Long id) {
        return articles.get(id);
    }

    public List<Article> findAll() {
        return new ArrayList<>(articles.values());
    }

    public Article deleteById(Long id) {
        return articles.remove(id);
    }
}