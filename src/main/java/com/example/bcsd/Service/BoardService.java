package com.example.bcsd.Service;

import com.example.bcsd.DTO.Board;
import com.example.bcsd.DTO.Article;
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

    @Transactional(readOnly = true)
    public Board findBoard(Long id) {
        Board board = boardRepository.findById(id);
        if (board == null) {
            throw new AllException(HttpStatus.NOT_FOUND, "존재하지 않는 게시판입니다.");
        }
        return board;
    }

    @Transactional
    public void deleteBoard(Long id) {
        Board existBoard = boardRepository.findById(id);
        if (existBoard == null) {
            throw new AllException(HttpStatus.NOT_FOUND, "존재하지 않는 게시판입니다.");
        }

        List<Article> articles = articleRepository.findByBoardId(id);
        if (!articles.isEmpty()) {
            throw new AllException(HttpStatus.BAD_REQUEST, "게시글이 존재하는 게시판은 삭제할 수 없습니다.");
        }

        boardRepository.delete(id);
    }
}
