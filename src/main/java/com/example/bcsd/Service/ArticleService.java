package com.example.bcsd.Service;

import com.example.bcsd.DTO.*;
import com.example.bcsd.Repository.ArticleRepository;
import com.example.bcsd.Repository.BoardRepository;
import com.example.bcsd.Repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public ArticleService(ArticleRepository articleRepository,
                          MemberRepository memberRepository,
                          BoardRepository boardRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    public List<ArticleResponseDTO> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        List<ArticleResponseDTO> responseList = new ArrayList<>();

        for (Article article : articles) {
            Member member = memberRepository.findById(article.getAuthorId());
            String authorName = (member != null) ? member.getName() : "알 수 없는 사용자";

            Board board = boardRepository.findById(article.getBoardId());
            String boardName = (board != null) ? board.getName() : "알 수 없는 게시판";

            ArticleResponseDTO dto = new ArticleResponseDTO(
                    boardName,
                    article.getTitle(),
                    authorName,
                    article.getCreatedAt(),
                    article.getContent()
            );
            responseList.add(dto);
        }
        return responseList;
    }

    public Article findArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public Article createArticle(ArticleRequestDTO request) {
        Long tmpAuthorId = 1L;

        Article newArticle = new Article(
                tmpAuthorId,
                request.boardId(),
                request.title(),
                request.content()
        );

        return articleRepository.save(newArticle);
    }

    public Article updateArticle(Long id, ArticleRequestDTO request) {
        Article existArticle = articleRepository.findById(id);

        if (existArticle == null) {
            return null;
        }

        existArticle.setBoardId(request.boardId());
        existArticle.setTitle(request.title());
        existArticle.setContent(request.content());
        existArticle.setUpdatedAt(LocalDateTime.now());

        return articleRepository.save(existArticle);
    }

    public Article deleteArticle(Long id) {
        return articleRepository.deleteById(id);
    }
}