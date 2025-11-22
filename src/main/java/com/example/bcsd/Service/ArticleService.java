package com.example.bcsd.Service;

import com.example.bcsd.DTO.Article;
import com.example.bcsd.DTO.ArticleRequestDTO;
import com.example.bcsd.DTO.Board;
import com.example.bcsd.Repository.ArticleRepository;
import com.example.bcsd.Repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;

    public ArticleService(ArticleRepository articleRepository, BoardRepository boardRepository) {
        this.articleRepository = articleRepository;
        this.boardRepository = boardRepository;
    }

    @Transactional(readOnly = true)
    public String getBoardName(Long boardId) {
        if (boardId == null) return "전체 게시판";
        Board board = boardRepository.findById(boardId);
        return (board != null) ? board.getName() : "모르는 게시판";
    }

    @Transactional(readOnly = true)
    public List<Article> findAllArticles(Long boardId) {
        if (boardId == null) {
            return articleRepository.findAll();
        }
        return articleRepository.findByBoardId(boardId);
    }

    @Transactional(readOnly = true)
    public Article findArticleById(Long id) {
        return articleRepository.findById(id);
    }

    @Transactional
    public Article createArticle(ArticleRequestDTO request) {
        Article newArticle = new Article(
                request.authorId(),
                request.boardId(),
                request.title(),
                request.content()
        );
        return articleRepository.insert(newArticle);
    }

    @Transactional
    public Article updateArticle(Long id, ArticleRequestDTO request) {
        Article existArticle = articleRepository.findById(id);
        if (existArticle == null) return null;

        existArticle.setTitle(request.title());
        existArticle.setContent(request.content());

        articleRepository.update(id, existArticle);
        return articleRepository.findById(id);
    }

    @Transactional
    public Article deleteArticle(Long id) {
        Article existArticle = articleRepository.findById(id);
        if (existArticle == null) return null;

        articleRepository.delete(id);
        return existArticle;
    }
}