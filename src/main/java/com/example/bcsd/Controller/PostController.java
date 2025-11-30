package com.example.bcsd.Controller;

import com.example.bcsd.DTO.Article;
import com.example.bcsd.Service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PostController {

    private final ArticleService articleService;

    public PostController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/posts")
    public String getPostsView(@RequestParam(required = false) Long boardId, Model model) {
        List<Article> articles = articleService.getAllArticles(boardId);
        String boardName = articleService.getBoardName(boardId);

        model.addAttribute("boardName", boardName);
        model.addAttribute("articles", articles);

        return "posts";
    }
}