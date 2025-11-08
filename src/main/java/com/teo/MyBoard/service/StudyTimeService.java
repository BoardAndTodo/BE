package com.teo.MyBoard.service;

import com.teo.MyBoard.repository.StudyTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudyTimeService {

    private final StudyTimeRepository studyTimeRepository;


    public void save(LocalDate date, int time) {
        // 저장 로직 구현

        if (time <= 0) {
            throw new IllegalStateException("저장할 시간값이 없습니다.");
        }
        studyTimeRepository.save(date, time);
    }

    public StudyTimeResponseDto findOne(LocalDate date) {

        Optional<StudyTimeResponseDto> dto = studyTimeRepository.findOne(date);

        StudyTimeResponseDto result = dto.orElseGet(() -> StudyTimeResponseDto.builder()
                .date(date)
                .time(0)
                .build());

        return result;
    }

    public List<StudyTimeResponseDto> findAll(int page, int size) {

        return studyTimeRepository.findAll(page,size);

    }
}