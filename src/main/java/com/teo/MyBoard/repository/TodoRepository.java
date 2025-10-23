package com.teo.MyBoard.repository;

import com.teo.MyBoard.dto.request.CreateTodoDto;
import com.teo.MyBoard.dto.request.TodoStatusUpdateDto;
import com.teo.MyBoard.dto.request.UpdateTodoDto;
import com.teo.MyBoard.dto.response.TodoResponseDto;
import com.teo.MyBoard.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TodoRepository {

    private final NamedParameterJdbcTemplate template;

    //저장
    public void save(CreateTodoDto dto) {

        String sql = """
                INSERT INTO todo (content,is_completed, created_at , updated_at)
                VALUES( :content, :is_completed, NOW(), NOW())
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("content", dto.getContent());
        params.addValue("is_completed", dto.isCompleted());

        template.update(sql, params);
    }
    //내용변경
    public void updateContent(UpdateTodoDto dto) {

        String sql = """
                UPDATE todo
                SET content = :content,
                updated_At = NOW()
                WHERE id = :id
                """;


        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("content", dto.getContent());
        params.addValue("id", dto.getId());

        int result = template.update(sql, params);

        if (result == 0) {
            throw new NotFoundException("수정할 todo가 존재하지 않습니다 id: " + dto.getId());
        }
    }

    //상태변경
    public int updateStatus(TodoStatusUpdateDto dto) {

        String sql = """
                UPDATE todo
                SET is_Completed = :isCompleted
                WHERE id = :id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("is_Completed", dto.isCompleted())
                .addValue("id", dto.getId());

        int result = template.update(sql, params);

        return result;


    }

    //아이디로 조회
    public Optional<TodoResponseDto> findById(Long id) {

        String sql = """
                SELECT * FROM todo WHERE id = :id         
                """;

        Map<String, Object> params
                = Map.of("id", id);


        List<TodoResponseDto> results = template.query(sql, params, (rs, roNum) ->
                TodoResponseDto.builder()
                        .id(rs.getLong("id"))
                        .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .content(rs.getString("content"))
                        .isCompleted(rs.getBoolean("is_completed"))
                        .build()

        );
        return results.stream().findFirst();
    }

    //전체조회

    /**
     *
     * @param page 현재 페이지
     * @param size 페이지당 항목 개수
     * @return TodoResponseDto 리스트(최신순 정렬)
     */
    public List<TodoResponseDto> findAll(int page, int size) {

        String sql = """
                SELECT * FROM todo
                ORDER BY created_at DESC
                LIMIT :limit OFFSET :offset
                """;

        int offset = page * size;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("limit", size)
                .addValue("offset", offset);


        return template.query(sql, params, (rs, roNum) ->
                TodoResponseDto.builder()
                        .isCompleted(rs.getBoolean("is_completed"))
                        .id(rs.getLong("id"))
                        .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .content(rs.getString("content"))
                        .build()
        );
    }


    public int delete(Long id) {

        String sql = """
                DELETE FROM todo
                WHERE id = :id
                """;

        Map<String, Object> params = Map.of("id", id);


        int result = template.update(sql, params);
        return result;



    }


}
