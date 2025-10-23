package com.teo.MyBoard.repository;


import com.teo.MyBoard.dto.request.BoardRequestDto;
import com.teo.MyBoard.dto.request.UpdateBoardDto;
import com.teo.MyBoard.dto.response.BoardResponseDto;
import com.teo.MyBoard.dto.response.TodoResponseDto;
import com.teo.MyBoard.entity.Board;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class BoardRepository {

    private final NamedParameterJdbcTemplate template;


    /**
     * 게시글 등록
     */

    public void save(BoardRequestDto boardRequestDto) {

        String sql = """
                INSERT INTO board (title, content, name, created_at, updated_at)
                VALUES(:title, :content, :name, NOW(), NOW())
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("title", boardRequestDto.getTitle());
        params.addValue("content", boardRequestDto.getContent());
        params.addValue("name", boardRequestDto.getName());

         template.update(sql, params);
    }

    /**
     *
     * @param id
     * @return 게시글 단건 조회
     */
    public Optional<BoardResponseDto> findById(Long id) {

        String sql = "SELECT * FROM board where id = :id";

        Map<String, Object>  params = Map.of("id", id);

        List<BoardResponseDto> results = template.query(sql, params, (rs, rowNum) ->
                BoardResponseDto.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .content(rs.getString("content"))
                        .name(rs.getString("name"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                        .build()
        );

        return results.stream().findFirst();

    }


    //게시물 전체 찾기(페이징처리)
    public List<BoardResponseDto> findAll(int page, int size) {


        String sql = """
                SELECT * FROM board 
                ORDER BY created_at DESC
                LIMIT :limit OFFSET :offset
                """;

        int offset = page * size;

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("limit", size);
        params.addValue("offset", offset);

        return template.query(sql, params, (rs, rowNum) ->
                BoardResponseDto.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .name(rs.getString("name"))
                        .content(rs.getString("content"))
                        .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .build()
        );


    }
    //게시글 삭제
    public int delete (Long id) {

        String sql = "DELETE FROM board where id = :id";

        Map<String, Object> param = Map.of("id", id);

        int result = template.update(sql, param);

        return result;
    }

    public int update(Long id, UpdateBoardDto dto) {

        String sql = """
                UPDATE board
                SET title = :title,
                content = :content,
                updated_at = NOW()
                WHERE id =:id
                """;

        MapSqlParameterSource params =
                new MapSqlParameterSource()
                        .addValue("id", id)
                        .addValue("title", dto.getTitle())
                        .addValue("content", dto.getContent());

        int result = template.update(sql, params);

        return result;
    }



}
