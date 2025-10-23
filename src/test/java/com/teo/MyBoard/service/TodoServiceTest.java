package com.teo.MyBoard.service;

import com.teo.MyBoard.dto.request.TodoStatusUpdateDto;
import com.teo.MyBoard.dto.response.TodoResponseDto;
import com.teo.MyBoard.exception.NotFoundException;
import com.teo.MyBoard.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;


    @Test
    void findById_成功時_TodoResponseDtoを返す() {

        //given
        TodoResponseDto responseDto = new TodoResponseDto()
                .builder()
                .id(1L)
                .content("test")
                .isCompleted(false)
                .build();
        given(todoRepository.findById(1L)).willReturn(Optional.of(responseDto));

        //when


        TodoResponseDto result = todoService.findById(1L);


        //then
        assertThat(result).isEqualTo(responseDto);
    }

    @Test
    void findById_IDが存在しない場合_例外発生() {

        //given

        given(todoRepository.findById(999L)).willReturn(Optional.empty());


        //when && then

        assertThatThrownBy(()->todoService.findById(999L))
                .isInstanceOf(NotFoundException.class);



    }

    @Test
    void updateStatus_IDが存在しない場合_例外発生(){


        //given
        TodoStatusUpdateDto dto = new TodoStatusUpdateDto();
        dto.setId(1L);
        dto.setCompleted(false);

        given(todoRepository.updateStatus(any(TodoStatusUpdateDto.class)))
                .willReturn(0);


        //when&then

        assertThatThrownBy(() -> todoService.updateStatus(dto))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void deleteById_IDが存在しない場合_例外発生() {

        //given
        given(todoRepository.delete(1L)).willReturn(0);

        //when&then
        assertThatThrownBy(() -> todoService.delete(1L))
                .isInstanceOf(NotFoundException.class);

        verify(todoRepository).delete(1L);

    }



}