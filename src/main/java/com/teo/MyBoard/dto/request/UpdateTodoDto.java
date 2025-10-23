package com.teo.MyBoard.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateTodoDto {
    private Long id;
    private String content;

}
