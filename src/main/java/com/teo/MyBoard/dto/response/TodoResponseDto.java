package com.teo.MyBoard.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TodoResponseDto {

    private Long id;
    private String content;
    private boolean isCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
