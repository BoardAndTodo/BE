package com.teo.MyBoard.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Todo {

    private Long id;
    private String content;
    private boolean isCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
