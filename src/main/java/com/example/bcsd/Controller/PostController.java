package com.example.bcsd.Controller;

import com.example.bcsd.DTO.ArticleResponseDTO; // Article -> ArticleResponseDTO
import com.example.bcsd.Service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class PostController {

    private final ArticleService articleService;

    public PostController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/posts")
    public String getPostsView(Model model) {
        List<ArticleResponseDTO> articles = articleService.findAllArticles();
        model.addAttribute("articles", articles);
        return "posts";
    }
}