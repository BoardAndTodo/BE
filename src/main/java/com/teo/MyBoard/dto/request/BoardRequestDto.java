package com.teo.MyBoard.dto.request;


import com.teo.MyBoard.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class BoardRequestDto {

    private String title;
    private String content;
    private String name;


    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .name(name)
                .build();
    }
}
