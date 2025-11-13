package com.example.bcsd.Controller;

import com.example.bcsd.DTO.Article;
import com.example.bcsd.DTO.ArticleRequestDTO;
import com.example.bcsd.Service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long id) {
        Article article = articleService.findArticleById(id);

        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(article);
    }

    @PostMapping
    public ResponseEntity<Article> postArticle(@RequestBody ArticleRequestDTO request) {
        Article newArticle = articleService.createArticle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newArticle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> putArticle(@PathVariable Long id, @RequestBody ArticleRequestDTO request) {
        Article updatedArticle = articleService.updateArticle(id, request);

        if (updatedArticle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        Article removedArticle = articleService.deleteArticle(id);

        if (removedArticle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}