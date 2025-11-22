package com.example.bcsd.Repository;

import com.example.bcsd.DTO.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ArticleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ArticleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Article insert(Article article) {
        String sql = "INSERT INTO article (author_id, board_id, title, content) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, article.getAuthorId());
            ps.setLong(2, article.getBoardId());
            ps.setString(3, article.getTitle());
            ps.setString(4, article.getContent());
            return ps;
        }, keyHolder);

        article.setId(keyHolder.getKey().longValue());
        return article;
    }

    public List<Article> findAll() {
        String sql = "SELECT * FROM article";
        return jdbcTemplate.query(sql, articleRowMapper());
    }

    public List<Article> findByBoardId(Long boardId) {
        String sql = "SELECT * FROM article WHERE board_id = ?";
        return jdbcTemplate.query(sql, articleRowMapper(), boardId);
    }

    public Article findById(Long id) {
        String sql = "SELECT * FROM article WHERE id = ?";
        List<Article> result = jdbcTemplate.query(sql, articleRowMapper(), id);
        return result.isEmpty() ? null : result.get(0);
    }

    public void update(Long id, Article article) {
        String sql = "UPDATE article SET title = ?, content = ? WHERE id = ?";
        jdbcTemplate.update(sql, article.getTitle(), article.getContent(), id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM article WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Article> articleRowMapper() {
        return (rs, rowNum) -> {
            Article article = new Article();
            article.setId(rs.getLong("id"));
            article.setAuthorId(rs.getLong("author_id"));
            article.setBoardId(rs.getLong("board_id"));
            article.setTitle(rs.getString("title"));
            article.setContent(rs.getString("content"));
            article.setCreatedDate(rs.getTimestamp("created_date"));
            article.setModifiedDate(rs.getTimestamp("modified_date"));
            return article;
        };
    }
}