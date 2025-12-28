package com.example.bcsd.Service;

import com.example.bcsd.DTO.Article;
import com.example.bcsd.DTO.ArticleRequestDTO;
import com.example.bcsd.DTO.Board;
import com.example.bcsd.DTO.Member;
import com.example.bcsd.Exception.AllException;
import com.example.bcsd.Repository.ArticleRepository;
import com.example.bcsd.Repository.BoardRepository;
import com.example.bcsd.Repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public String getBoardName(Long boardId) {
        if (boardId == null) return "전체 게시판";
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new AllException(HttpStatus.NOT_FOUND, "존재하지 않는 게시판입니다."));
        return board.getName();
    }

    @Transactional(readOnly = true)
    public List<Article> getAllArticles(Long boardId) {
        if (boardId == null) {
            return articleRepository.findAll();
        }
        if (!boardRepository.existsById(boardId)) {
            throw new AllException(HttpStatus.NOT_FOUND, "존재하지 않는 게시판입니다.");
        }
        return articleRepository.findAllByBoardId(boardId);
    }

    @Transactional(readOnly = true)
    public Article findArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new AllException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."));
    }

    @Transactional
    public Article createArticle(ArticleRequestDTO request) {
        if (request.title() == null || request.title().trim().isEmpty() ||
                request.content() == null || request.content().trim().isEmpty() ||
                request.authorId() == null || request.boardId() == null) {
            throw new AllException(HttpStatus.BAD_REQUEST, "필수 값이 누락되었거나 공백입니다.");
        }

        Member author = memberRepository.findById(request.authorId())
                .orElseThrow(() -> new AllException(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."));

        Board board = boardRepository.findById(request.boardId())
                .orElseThrow(() -> new AllException(HttpStatus.BAD_REQUEST, "존재하지 않는 게시판입니다."));

        Article newArticle = new Article(
                author,
                board,
                request.title(),
                request.content()
        );
        return articleRepository.save(newArticle);
    }

    @Transactional
    public Article updateArticle(Long id, ArticleRequestDTO request) {
        Article existArticle = articleRepository.findById(id)
                .orElseThrow(() -> new AllException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."));

        if (request.title() != null && !request.title().trim().isEmpty()) {
            existArticle.setTitle(request.title());
        }
        if (request.content() != null && !request.content().trim().isEmpty()) {
            existArticle.setContent(request.content());
        }
        if (request.boardId() != null) {
            Board newBoard = boardRepository.findById(request.boardId())
                    .orElseThrow(() -> new AllException(HttpStatus.BAD_REQUEST, "존재하지 않는 게시판입니다."));
            existArticle.setBoard(newBoard);
        }

        return existArticle;
    }

    @Transactional
    public void deleteArticle(Long id) {
        Article existArticle = articleRepository.findById(id)
                .orElseThrow(() -> new AllException(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."));

        articleRepository.delete(existArticle);
    }
}

