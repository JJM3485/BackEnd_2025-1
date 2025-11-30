package com.example.bcsd.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ArticleRequestDTO(
        @JsonProperty("board_id") Long boardId,
        @JsonProperty("author_id") Long authorId,
        String title,
        String content
) {
}
