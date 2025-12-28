package com.example.bcsd.Service;

import com.example.bcsd.DTO.Article;
import com.example.bcsd.DTO.Board;
import com.example.bcsd.Exception.AllException;
import com.example.bcsd.Repository.ArticleRepository;
import com.example.bcsd.Repository.BoardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ArticleRepository articleRepository;

    public BoardService(BoardRepository boardRepository, ArticleRepository articleRepository) {
        this.boardRepository = boardRepository;
        this.articleRepository = articleRepository;
    }

    @Transactional
    public Board createBoard(Board board) {
        if (board.getName() == null || board.getName().trim().isEmpty()) {
            throw new AllException(HttpStatus.BAD_REQUEST, "게시판 이름은 필수입니다.");
        }
        return boardRepository.save(board);
    }

    @Transactional
    public void deleteBoard(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new AllException(HttpStatus.NOT_FOUND, "존재하지 않는 게시판입니다.");
        }

        List<Article> articles = articleRepository.findAllByBoardId(id);
        if (!articles.isEmpty()) {
            throw new AllException(HttpStatus.BAD_REQUEST, "게시글이 존재하는 게시판은 삭제할 수 없습니다.");
        }

        boardRepository.deleteById(id);
    }
}

