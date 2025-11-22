package com.example.bcsd.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class Article {

    private Long id;

    @JsonProperty("author_id")
    private Long authorId;

    @JsonProperty("board_id")
    private Long boardId;

    private String title;
    private String content;

    @JsonProperty("created_date")
    private Timestamp createdDate;

    @JsonProperty("modified_date")
    private Timestamp modifiedDate;

    public Article(Long authorId, Long boardId, String title, String content) {
        this.authorId = authorId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
    }
}