package com.teo.MyBoard.service;

import com.teo.MyBoard.dto.request.BoardRequestDto;
import com.teo.MyBoard.dto.request.UpdateBoardDto;
import com.teo.MyBoard.dto.response.BoardResponseDto;
import com.teo.MyBoard.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository; //가짜 DB

    @InjectMocks
    private BoardService boardService; //테스트 대상


    @Test
    void findById_성공_시_BoardResponseDto_반환() {

        // given
        BoardResponseDto board = BoardResponseDto.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .name("작성자")
                .build();

        given(boardRepository.findById(1L))
                .willReturn(Optional.of(board));

        // when
        BoardResponseDto result = boardService.findById(1L);

        // then
        assertThat(result.getTitle())
                .isEqualTo("제목");   // 제목이 정상적으로 반환되는지 검증
        verify(boardRepository).findById(1L);
        assertThat(result.getContent())
                .isEqualTo("내용");   // 내용이 정상적으로 반환되는지 검증
    }

    @Test
    void findById_아이디없을때_예외발생() {
        // given (조건 세팅)
        given(boardRepository.findById(999L))
                .willReturn(Optional.empty()); // 존재하지 않는 ID라고 가정

        // when & then (예외 검증)
        assertThatThrownBy(() -> boardService.findById(999L))
                .isInstanceOf(IllegalArgumentException.class)  // 예외 타입 확인
                .hasMessageContaining("해당 게시글이 존재하지 않습니다.");       // 예외 메시지 내용 확인

        // verify (메서드 호출 여부 검증)
        verify(boardRepository).findById(999L);
    }
    @Test
    void deleteById_아이디없을때_예외발생() {
        // given
        // boardRepository.deleteById(999L)가 호출될 때 IllegalArgumentException 발생하도록 설정
        willThrow(new IllegalArgumentException("삭제할 게시글이 존재하지 않습니다."))
                .given(boardRepository)
                .delete(999L);

        // when & then
        assertThatThrownBy(() -> boardService.deleteById(999L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("삭제할 게시글이 존재하지 않습니다.");

        // verify
        verify(boardRepository).delete(999L); // Repository 메서드 호출 여부 검증
    }

    @Test
    void updateById_아이디없을때_예외발생() {
        // given

        UpdateBoardDto dto = new UpdateBoardDto();
        dto.setContent("내용");
        dto.setTitle("제목");

        // boardRepository.updateById(999L, dto)가 호출될 때 IllegalStateException 발생하도록 설정
        willThrow(new IllegalStateException("수정할 게시글이 존재하지 않습니다."))
                .given(boardRepository)
                .update(999L, dto); // null 대신 실제 UpdateBoardDto 객체를 전달할 수 있음

        // when & then
        assertThatThrownBy(() -> boardService.updateById(999L, dto)) // null 대신 실제 UpdateBoardDto 객체를 전달할 수 있음
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("수정할 게시글이 존재하지 않습니다.");

        // verify
        verify(boardRepository).update(999L, dto); // null 대신 실제 UpdateBoardDto 객체를 전달할 수 있음
    }

    @Test
    void repository_정상호출_검증() {
        //given

        BoardRequestDto dto = new BoardRequestDto();


        //when
        boardService.save(dto);
        //then
        verify(boardRepository).save(dto);



    }


}
