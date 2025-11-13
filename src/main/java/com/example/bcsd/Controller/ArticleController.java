package com.example.bcsd.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.example.bcsd.DTO.ArticleRequestDTO;
import com.example.bcsd.DTO.ArticleEntity;

@RestController
public class ArticleController {

    private final Map<Long, ArticleEntity> articles = new HashMap<>();
    private final AtomicLong idCount = new AtomicLong();

    @GetMapping("/article/{id}")
    public ResponseEntity<ArticleEntity> getArticle(@PathVariable Long id) {
        ArticleEntity article = articles.get(id);

        if (article == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(article);
    }

    @PostMapping("/article")
    public ResponseEntity<ArticleEntity> postArticle(@RequestBody ArticleRequestDTO request) {
        long newId = idCount.incrementAndGet();
        ArticleEntity newArticle = new ArticleEntity(newId, request.description());
        articles.put(newId, newArticle);

        return ResponseEntity.status(HttpStatus.CREATED).body(newArticle);
    }

    @PutMapping("/article/{id}")
    public ResponseEntity<ArticleEntity> putArticle(@PathVariable Long id, @RequestBody ArticleRequestDTO request) {
        if (!articles.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        ArticleEntity updateArticle = new ArticleEntity(id, request.description());
        articles.put(id, updateArticle);

        return ResponseEntity.ok(updateArticle);
    }

    @DeleteMapping("/article/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        ArticleEntity removedArticle = articles.remove(id);

        if (removedArticle == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}