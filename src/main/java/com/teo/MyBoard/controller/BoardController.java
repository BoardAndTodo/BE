package com.teo.MyBoard.controller;

import com.teo.MyBoard.dto.request.BoardRequestDto;
import com.teo.MyBoard.dto.request.UpdateBoardDto;
import com.teo.MyBoard.dto.response.BoardResponseDto;
import com.teo.MyBoard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {


    private final BoardService boardService;


    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody BoardRequestDto dto) {

        boardService.save(dto);
        return ResponseEntity.ok("게시글 작성 완료");
    }


    @GetMapping("/findById/{id}")
    public ResponseEntity<BoardResponseDto> findById(@PathVariable Long id) {
        BoardResponseDto boardResponseDto = boardService.findById(id);

        return ResponseEntity.ok(boardResponseDto);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<BoardResponseDto>> findAll(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        List<BoardResponseDto> dtoList = boardService.findAll(page, size);

        return ResponseEntity.ok(dtoList);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {

        boardService.deleteById(id);
        return ResponseEntity.ok("게시글이 삭제되었습니다.");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id,
                                         @RequestBody UpdateBoardDto dto) {

        boardService.updateById(id,dto);
        return ResponseEntity.ok("게시글이 업데이트되었습니다.");
    }
}
