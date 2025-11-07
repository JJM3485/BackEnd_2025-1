package com.example.bcsd;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ArticleController {

    private final Map<Long, Article> articles = new HashMap<>();
    private final AtomicLong idCount = new AtomicLong();

    @GetMapping("/article/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long id) {
        Article article = articles.get(id);

        if (article == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(article);
    }

    @PostMapping("/article")
    public ResponseEntity<Article> postArticle(@RequestBody ArticleRequestDTO request) {
        long newId = idCount.incrementAndGet();
        Article newArticle = new Article(newId, request.description());
        articles.put(newId, newArticle);

        return ResponseEntity.status(HttpStatus.CREATED).body(newArticle);
    }

    @PutMapping("/article/{id}")
    public ResponseEntity<Article> putArticle(@PathVariable Long id, @RequestBody ArticleRequestDTO request) {
        if (!articles.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        Article updateArticle = new Article(id, request.description());
        articles.put(id, updateArticle);

        return ResponseEntity.ok(updateArticle);
    }

    @DeleteMapping("/article/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        Article removedArticle = articles.remove(id);

        if (removedArticle == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    public record Article(Long id, String description) {
    }

    public record ArticleRequestDTO(String description) {
    }
}