package com.example.bcsd.DTO;

public record ArticleRequestDTO(
        Long boardId,
        String title,
        String content) {
}