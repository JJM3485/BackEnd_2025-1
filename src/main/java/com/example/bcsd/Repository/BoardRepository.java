package com.example.bcsd.Repository;

import com.example.bcsd.DTO.Board;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardRepository {

    private final JdbcTemplate jdbcTemplate;

    public BoardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Board findById(Long id) {
        String sql = "SELECT * FROM board WHERE id = ?";
        List<Board> result = jdbcTemplate.query(sql, boardRowMapper(), id);
        return result.isEmpty() ? null : result.get(0);
    }

    private RowMapper<Board> boardRowMapper() {
        return (rs, rowNum) -> {
            Board board = new Board();
            board.setId(rs.getLong("id"));
            board.setName(rs.getString("name"));
            return board;
        };
    }
}