package com.example.bcsd.Controller;

import com.example.bcsd.DTO.Article;
import com.example.bcsd.DTO.ArticleRequestDTO;
import com.example.bcsd.Service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles(@RequestParam(required = false) Long boardId) {
        return ResponseEntity.ok(articleService.findAllArticles(boardId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticle(@PathVariable Long id) {
        Article article = articleService.findArticleById(id);
        return (article != null) ? ResponseEntity.ok(article) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Article> postArticle(@RequestBody ArticleRequestDTO request) {
        Article newArticle = articleService.createArticle(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newArticle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> putArticle(@PathVariable Long id, @RequestBody ArticleRequestDTO request) {
        Article updatedArticle = articleService.updateArticle(id, request);
        return (updatedArticle != null) ? ResponseEntity.ok(updatedArticle) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        Article removedArticle = articleService.deleteArticle(id);
        return (removedArticle != null) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}