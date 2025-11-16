package com.example.bcsd.DTO;

import java.time.LocalDateTime;

public record ArticleResponseDTO(
        String boardName,
        String title,
        String author,
        LocalDateTime date,
        String content) {
}