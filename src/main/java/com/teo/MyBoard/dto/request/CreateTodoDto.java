package com.teo.MyBoard.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTodoDto {

    private String content;
    private boolean isCompleted = true;

}
