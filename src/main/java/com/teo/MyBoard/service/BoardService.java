package com.teo.MyBoard.service;

import com.teo.MyBoard.dto.request.BoardRequestDto;
import com.teo.MyBoard.dto.request.UpdateBoardDto;
import com.teo.MyBoard.dto.response.BoardResponseDto;
import com.teo.MyBoard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardRequestDto dto) {
        boardRepository.save(dto);
    }


    public BoardResponseDto findById(Long id) {
        BoardResponseDto boardResponseDto = boardRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        return boardResponseDto;
    }

    public List<BoardResponseDto> findAll(int page, int size) {

        List<BoardResponseDto> all = boardRepository.findAll(page, size);

        if (all.isEmpty()) {
            new IllegalArgumentException("게시글이 없습니다.");
        }
        return all;
    }

    public void deleteById(Long id) {

        int result = boardRepository.delete(id);

        if (result == 0) {
            throw new IllegalArgumentException("삭제할 게시글이 존재하지 않습니다. id=" + id);
        }

    }

    public void updateById(Long id, UpdateBoardDto dto) {

        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("제목은 비어 있을 수 없습니다.");
        }
        if(dto.getContent() == null || dto.getContent().isBlank()) {
            throw new IllegalArgumentException("내용은 비어 있을 수 없습니다.");
        }

        int result = boardRepository.update(id, dto);
        if (result == 0) {
            throw new IllegalStateException("수정할 게시글이 존재하지 않습니다. id=" + id);
        }


    }
}
