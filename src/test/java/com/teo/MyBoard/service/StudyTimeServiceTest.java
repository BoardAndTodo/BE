package com.teo.MyBoard.service;

import com.teo.MyBoard.repository.StudyTimeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StudyTimeServiceTest {


    @Mock
    private StudyTimeRepository studyTimeRepository;

    @InjectMocks
    private StudyTimeService studyTimeService;


    @Test
    void save_정상입력_저장성공() {

        //given
        LocalDate date = LocalDate.now();

        int time = 120;

        //when
        studyTimeRepository.save(date, time);


        //then
        verify(studyTimeRepository).save(date, time);

    }

    @Test
    void save_시간값없으면_예외발생() {

        //given
        LocalDate date = LocalDate.now();
        int time = 0;

        //when&then
        assertThatThrownBy(() -> studyTimeService.save(date, time))
                .isInstanceOf(IllegalStateException.class);
    }


    @Test
    void findOne_저장된값있으면_조회성공() {


        //given
        LocalDate date = LocalDate.now();
        int time = 120;
        StudyTimeResponseDto dto = new StudyTimeResponseDto(date, time);

        given(studyTimeRepository.findOne(date))
                .willReturn(Optional.of(dto));

        //when

        StudyTimeResponseDto result = studyTimeService.findOne(date);

        //then

        assertThat(result).isEqualTo(dto);
        assertThat(result.getDate()).isEqualTo(date);
        assertThat(result.getTime()).isEqualTo(time);
        verify(studyTimeRepository).findOne(date);

    }


    @Test
    void findOne_저장된값없으면_시간_0_반환() {

        //given
        LocalDate date = LocalDate.now();
        int time = 0;

        given(studyTimeRepository.findOne(date))
                .willReturn(Optional.empty());

        //when
        StudyTimeResponseDto result = studyTimeService.findOne(date);

        //then

        assertThat(result.getTime()).isEqualTo(0);


    }

    @Test
    void findAll_저장된값있으면_조회성공() {

        //given
        LocalDate date1 = LocalDate.of(2025, 11, 6);
        LocalDate date2 = LocalDate.of(2025, 11, 7);
        int time1 = 150;
        int time2 = 200;
        int page = 0;
        int size = 10;

        StudyTimeResponseDto dto1 = new StudyTimeResponseDto(date1, time1);
        StudyTimeResponseDto dto2 = new StudyTimeResponseDto(date2, time2);
        given(studyTimeRepository.findAll(page , size))
                .willReturn(List.of(dto1, dto2));

        //when

        List<StudyTimeResponseDto> result = studyTimeService.findAll(page,size);

        //then
        verify(studyTimeRepository).findAll(page,size);
        assertThat(studyTimeService.findAll(page,size).size()).isEqualTo(2);

        assertThat(result)
                .containsExactlyInAnyOrder(dto1, dto2);



    }

    @Test
    void findAll_저장된값없으면_빈리스트반환() {

        //given
        int page = 0;
        int size = 10;
        given(studyTimeRepository.findAll(page,size)).willReturn(List.of());

        //when
        List<StudyTimeResponseDto> result = studyTimeService.findAll(page,size);

        assertThat(result).isEmpty();
    }
}



