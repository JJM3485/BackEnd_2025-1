package com.example.bcsd.Repository;

import com.example.bcsd.DTO.Board;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BoardRepository {

    private final Map<Long, Board> boards = new HashMap<>();
    private final AtomicLong idCount = new AtomicLong();

    public Board save(Board board) {
        if (board.getId() == null) {
            long newId = idCount.incrementAndGet();
            board.setId(newId);
        }
        boards.put(board.getId(), board);
        return board;
    }

    public Board findById(Long id) {
        return boards.get(id);
    }

    @PostConstruct
    public void initData() {
        Board board1 = new Board("자유게시판");
        save(board1);

        Board board2 = new Board("유자게시판");
        save(board2);
    }
}