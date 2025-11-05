package com.teo.MyBoard.controller;


import com.teo.MyBoard.MyBoardApplication;
import com.teo.MyBoard.dto.request.BoardRequestDto;
import com.teo.MyBoard.dto.request.UpdateBoardDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

//TestRestTemplate 사용
@SpringBootTest(
        classes = MyBoardApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BoardIntegrationTest {
//    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Test
    void 게시글등록_성공하면200을반환(
            @Autowired TestRestTemplate client
    ) {
        //given
        BoardRequestDto dto = new BoardRequestDto();
        dto.setTitle("제목");
        dto.setContent("내용");
        dto.setName("작성자");

       //when&then
        ResponseEntity<Void> response = client.postForEntity(
                "/api/boards/save",
                dto,
                Void.class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(200);

    }
//    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Test
    void 게시글_등록시_제목이_없으면_400을_반환한다(
            @Autowired TestRestTemplate client
    ) {
        //given
        BoardRequestDto dto = new BoardRequestDto();
        dto.setTitle(null);
        dto.setContent("내용");
        dto.setName("작성자");

        //when&then
        ResponseEntity<Void> response = client.postForEntity(
                "/api/boards/save",
                dto,
                Void.class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(400);

    }
//    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Test
    void 게시글_등록시_내용이_없으면_400을_반환한다(
            @Autowired TestRestTemplate client
    ) {
        //given
        BoardRequestDto dto = new BoardRequestDto();
        dto.setTitle("제목");
        dto.setContent(null);
        dto.setName("작성자");

        //when&then
        ResponseEntity<Void> response = client.postForEntity(
                "/api/boards/save",
                dto,
                Void.class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(400);

    }
//    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Test
    void 게시글조회_성공하면200을반환(@Autowired TestRestTemplate client)

     {
        //given
         BoardRequestDto dto = new BoardRequestDto();
            dto.setTitle("제목");
            dto.setContent("내용");
            dto.setName("작성자");
         client.postForEntity("/api/boards/save", dto, String.class);

        //when&then
        ResponseEntity<String> response = client.getForEntity(
                "/api/boards/findById/" + 1,
                String.class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(200);

    }
//    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Test
    void 게시글조회_아이디없을때_예외발생(@Autowired TestRestTemplate client) {
        //given
        BoardRequestDto dto = new BoardRequestDto();
        dto.setTitle("제목");
        dto.setContent("내용");
        dto.setName("작성자");
        client.postForEntity("/api/boards/save", dto, String.class);

        //when&then
        ResponseEntity<String> response = client.getForEntity(
                "/api/boards/findById/" + 2,
                String.class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(400);

    }
//    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Test
    void put_게시글수정_성공하면200을반환(@Autowired TestRestTemplate client) {
        //given
        BoardRequestDto dto = new BoardRequestDto();
        dto.setTitle("제목");
        dto.setContent("내용");
        dto.setName("작성자");
        client.postForEntity("/api/boards/save", dto, String.class);

        BoardRequestDto updateDto = new BoardRequestDto();
        updateDto.setTitle("수정된 제목");
        updateDto.setContent("수정된 내용");
        updateDto.setName("수정된 작성자");

        //when&then
        client.put(
                "/api/boards/update/" + 1,
                updateDto,
                String.class
        );

        ResponseEntity<String> response = client.getForEntity(
                "/api/boards/findById/" + 1,
                String.class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).contains("수정된 제목");
    }
//    @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
    @Test
    void put_게시글수정_제목이나내용이없으면_400을반환(@Autowired TestRestTemplate client) {
        //given
        BoardRequestDto dto = new BoardRequestDto();
        dto.setTitle("제목");
        dto.setContent("내용");
        dto.setName("작성자");
        client.postForEntity("/api/boards/save", dto, String.class);

        UpdateBoardDto updateDto = new UpdateBoardDto();
        updateDto.setTitle(null);
        updateDto.setContent("수정된 내용");

        //when&then
        ResponseEntity<String> response = client.exchange(
                "/api/boards/update/" + 1,
                HttpMethod.PUT,
                new HttpEntity<>(updateDto),
                String.class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    void put_게시글수정_존재하지않는게시글이면_404를반환(@Autowired TestRestTemplate client) {
        //given
//        BoardRequestDto dto = new BoardRequestDto();
//        dto.setTitle("제목");
//        dto.setContent("내용");
//        dto.setName("작성자");
//        client.postForEntity("/api/boards/save", dto, String.class);

        UpdateBoardDto updateDto = new UpdateBoardDto();
        updateDto.setTitle("수정된 제목");
        updateDto.setContent("수정된 내용");

        //when&then
        client.exchange(
                "/api/boards/update/" + 1,
                HttpMethod.PUT,
                new HttpEntity<>(updateDto),
                String.class
        );

        ResponseEntity<String> response = client.getForEntity(
                "/api/boards/findById/" + 1,
                String.class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    void delete_게시글삭제_성공하면200을반환(@Autowired TestRestTemplate client) {
        //given
        BoardRequestDto dto = new BoardRequestDto();
        dto.setTitle("제목");
        dto.setContent("내용");
        dto.setName("작성자");
        client.postForEntity("/api/boards/save", dto, String.class);

        //when&then
        ResponseEntity<String> response = client.exchange(
                "/api/boards/delete/" + 1
                , HttpMethod.DELETE
                , null
                , String.class
        );


        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void delete_게시글삭제_존재하지않는게시글이면_404를반환(@Autowired TestRestTemplate client) {


        //given
        BoardRequestDto dto = new BoardRequestDto();
        dto.setTitle("제목");
        dto.setContent("내용");
        dto.setName("작성자");
        client.postForEntity("/api/boards/save", dto, String.class);


        //when&then
        ResponseEntity<String> response = client.exchange(
                "/api/boards/delete/" + 2
                , HttpMethod.DELETE
                , null
                , String.class
        );
        assertThat(response.getStatusCode().value()).isEqualTo(400);

    }



}
