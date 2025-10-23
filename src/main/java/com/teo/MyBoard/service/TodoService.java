package com.teo.MyBoard.service;

import com.teo.MyBoard.dto.request.CreateTodoDto;
import com.teo.MyBoard.dto.request.TodoStatusUpdateDto;
import com.teo.MyBoard.dto.request.UpdateTodoDto;
import com.teo.MyBoard.dto.response.TodoResponseDto;
import com.teo.MyBoard.exception.NotFoundException;
import com.teo.MyBoard.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public void create(CreateTodoDto dto) {

        if(dto.getContent() == null || dto.getContent().isBlank()) {
            throw new IllegalArgumentException("Todoの内容は必須です。");
        }

        todoRepository.save(dto);

    }

    public TodoResponseDto findById(Long id) {
        Optional<TodoResponseDto> result = todoRepository.findById(id);

        return result
                .orElseThrow(() -> new NotFoundException
                        ("해당 아이디 : " + id + " 에 해당하는 todo를 찾을 수 없습니다."));

    }

    //전체 todo 조회 페이징처리
    public List<TodoResponseDto> findAll(int page, int size) {

        List<TodoResponseDto> all = todoRepository.findAll(page, size);
        return all;

    }

    public void updateStatus(TodoStatusUpdateDto dto) {

        int result = todoRepository.updateStatus(dto);

        if (result == 0) {
            throw new NotFoundException("업데이트할 todo가 존재하지 않습니다 id : " + dto.getId());
        }

    }


    public void updateTodo(UpdateTodoDto dto) {
        todoRepository.updateContent(dto);

    }

    public void delete(Long id) {


        int result = todoRepository.delete(id);

        if (result == 0) {
           throw new NotFoundException("삭제할 게시글을 찾을 수 없습니다 id: " + result);
        }


    }

}
