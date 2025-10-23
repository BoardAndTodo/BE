package com.teo.MyBoard.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
