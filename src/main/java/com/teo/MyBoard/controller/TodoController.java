package com.teo.MyBoard.controller;

import com.teo.MyBoard.dto.request.CreateTodoDto;
import com.teo.MyBoard.dto.request.UpdateTodoDto;
import com.teo.MyBoard.dto.response.TodoResponseDto;
import com.teo.MyBoard.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    //todo 생성
    @PostMapping("/create")
    public ResponseEntity<String> createTodo(@RequestBody CreateTodoDto dto) {
        todoService.create(dto);
        return ResponseEntity.ok("Todo 작성완료");
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<TodoResponseDto> findById(@PathVariable Long id) {
        TodoResponseDto result
                = todoService.findById(id);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<TodoResponseDto>> findByAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        List<TodoResponseDto> result = todoService.findAll(page, size);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody UpdateTodoDto dto
                                      ) {
        todoService.updateTodo(dto);
        return ResponseEntity.ok("todo가 업데이트 되었습니다.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        todoService.delete(id);
       return ResponseEntity.ok("todo가 삭제되었습니다");
    }
}

