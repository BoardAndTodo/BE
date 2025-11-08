package com.teo.MyBoard.repository;

import com.teo.MyBoard.service.StudyTimeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StudyTimeRepository {

    private final NamedParameterJdbcTemplate template;

    public void save (LocalDate date, int time)
    {

        String sql = """
                INSERT INTO study_time (study_date, study_seconds, created_at)
                VALUES ( :study_date, :study_seconds, NOW())
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("study_date", date);
        params.addValue("study_seconds", time);

        template.update(sql, params);


    }

    public Optional<StudyTimeResponseDto> findOne(LocalDate date) {


        String sql = """
                SELECT study_date, study_seconds FROM study_time
                WHERE study_date = :study_date
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("study_date", date);


        StudyTimeResponseDto result = template.queryForObject(sql, params, (rs, rowNum) ->
                StudyTimeResponseDto.builder()
                        .date(rs.getDate("study_date").toLocalDate())
                        .time(rs.getInt("study_seconds"))
                        .build()
        );


        return Optional.of(result);
    }


    public List<StudyTimeResponseDto> findAll(int page, int size) {

        int offset = (page - 1) * size;

        String sql = """
                
                SELECT * FROM study_time
                LIMIT :size OFFSET :offset
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("size",size);
        params.addValue("offset", offset);


        List<StudyTimeResponseDto> result = template.query(sql,params, (rs, rowNum) ->
                StudyTimeResponseDto.builder()
                        .date(rs.getDate("study_date").toLocalDate())
                        .time(rs.getInt("study_seconds"))
                        .build());

        return result;
    }
}
