package com.teo.MyBoard.controller;

import com.teo.MyBoard.service.StudyTimeResponseDto;
import com.teo.MyBoard.service.StudyTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/studyTime")
public class StudyTimeController {

    private final StudyTimeService studyTimeService;


    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestParam String date,
                                  @RequestParam int time) {
        try {
            LocalDate localDate = LocalDate.parse(date); // 문자열 -> LocalDate
            studyTimeService.save(localDate, time);
            return ResponseEntity.ok("저장 완료");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("잘못된 날짜 형식: " + date);
        }
    }

    @GetMapping("/findOne")
    public ResponseEntity<?> findOne(LocalDate date) {

        StudyTimeResponseDto result = studyTimeService.findOne(date);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(@RequestParam int page,
                                     @RequestParam int size) {
        List<StudyTimeResponseDto> result = studyTimeService.findAll(page, size);
        return ResponseEntity.ok(result);
    }
}
