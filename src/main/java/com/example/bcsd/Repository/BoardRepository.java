package com.example.bcsd.Repository;

import com.example.bcsd.DTO.Board;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class BoardRepository {

    private final JdbcTemplate jdbcTemplate;

    public BoardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Board save(Board board) {
        String sql = "INSERT INTO board (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, board.getName());
            return ps;
        }, keyHolder);

        board.setId(keyHolder.getKey().longValue());
        return board;
    }

    public Board findById(Long id) {
        if (id == null) return null;
        String sql = "SELECT * FROM board WHERE id = ?";
        List<Board> result = jdbcTemplate.query(sql, boardRowMapper(), id);
        return result.isEmpty() ? null : result.get(0);
    }

    public void delete(Long id) {
        if (id == null) return;
        String sql = "DELETE FROM board WHERE id = ?";
        jdbcTemplate.update(sql, id);
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
