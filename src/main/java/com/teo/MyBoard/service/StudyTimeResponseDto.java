package com.teo.MyBoard.service;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Builder
public class StudyTimeResponseDto {

    private LocalDate date;
    private int time;



}
