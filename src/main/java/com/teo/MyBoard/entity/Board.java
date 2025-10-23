package com.teo.MyBoard.entity;


import com.teo.MyBoard.dto.response.BoardResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Board {

    private Long id;                 // 게시글 번호 (PK)
    private String title;            // 제목
    private String content;          // 내용
    private String name;             // 작성자
    private LocalDateTime createdAt; // 생성일
    private LocalDateTime updatedAt; // 수정일


    public BoardResponseDto toDto() {
        return BoardResponseDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .name(name)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
