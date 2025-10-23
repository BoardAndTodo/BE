package com.teo.MyBoard.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoStatusUpdateDto {

    private Long id;
    private boolean isCompleted;
}
