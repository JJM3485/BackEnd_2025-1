package com.example.bcsd.Service;

import com.example.bcsd.DTO.Article;
import com.example.bcsd.DTO.ArticleRequestDTO;
import com.example.bcsd.Repository.ArticleRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Article findArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public Article createArticle(ArticleRequestDTO request) {
        Article newArticle = new Article(null, request.description());
        return articleRepository.save(newArticle);
    }

    public Article updateArticle(Long id, ArticleRequestDTO request) {
        Article existArticle = articleRepository.findById(id);

        if (existArticle == null) {
            return null;
        }
        existArticle.setDescription(request.description());
        return articleRepository.save(existArticle);
    }

    public Article deleteArticle(Long id) {
        return articleRepository.deleteById(id);
    }
}