package com.teo.MyBoard.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyTime {
    private Long id;
    private Long userId;
    private LocalDate studyDate;
    private int studySeconds;
    private LocalDateTime createdAt;
}
