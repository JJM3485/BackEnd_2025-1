package com.example.bcsd.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_id", nullable = false)
    @JsonProperty("author_id")
    private Long authorId;

    @Column(name = "board_id", nullable = false)
    @JsonProperty("board_id")
    private Long boardId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "created_date")
    @JsonProperty("created_date")
    private Timestamp createdDate;

    @Column(name = "modified_date")
    @JsonProperty("modified_date")
    private Timestamp modifiedDate;

    public Article(Long authorId, Long boardId, String title, String content) {
        this.authorId = authorId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
    }
}

