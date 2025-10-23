package com.teo.MyBoard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teo.MyBoard.dto.request.CreateTodoDto;
import com.teo.MyBoard.dto.request.TodoStatusUpdateDto;
import com.teo.MyBoard.dto.request.UpdateTodoDto;
import com.teo.MyBoard.service.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Todo 통합 테스트\"/api/todo\"")
@ActiveProfiles("test")
public class TodoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TodoService todoService;

    @Test
    void post_Todo作成_成功時_200を返す() throws Exception {
        //given

        CreateTodoDto dto = new CreateTodoDto();
        dto.setContent("テストTODO");
        dto.setCompleted(false);


        //when
        mockMvc.perform(
                        post("/api/todo/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    void post_Todo作成_内容が空の場合ばあい_400を返す() throws Exception {

        //given
        CreateTodoDto dto = new CreateTodoDto();
        dto.setContent("");
        dto.setCompleted(false);

        //when&then

        mockMvc.perform(
                        post("/api/todo/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    @Test
    void get_Todo取得_成功時_200を返す() throws Exception {

        //given
        CreateTodoDto dto = new CreateTodoDto();
        dto.setContent("テストTODO");
        dto.setCompleted(false);
        todoService.create(dto);


        //when&then

        mockMvc.perform(
                        get("/api/todo/findById/" + 1)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value("テストTODO"))
                .andExpect(jsonPath("$.completed").value(false))
                .andDo(print()
                );


    }

    @Test
    void get_Todo取得_存在しないIDの場合_404を返す() {

        //when&then

        try {
            mockMvc.perform(
                            get("/api/todo/findById/" + 999)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isNotFound())
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Test
    void get_Todo一覧取得_成功時_200を返す() {
        //given
        for (int i = 1; i <= 15; i++) {
            CreateTodoDto dto = new CreateTodoDto();
            dto.setContent("テストTODO" + i);
            dto.setCompleted(false);
            todoService.create(dto);
        }

        //when&then

        try {
            mockMvc.perform(
                            get("/api/todo/findAll")
                                    .param("page", "0")
                                    .param("size", "10")
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(10))
                    .andDo(print());
        } catch (Exception e) {
            e.printStackTrace();


        }

    }

    @Test
    void put_Todo内容更新_成功時_200を返す() throws Exception {

        //given
        CreateTodoDto dto = new CreateTodoDto();
        dto.setContent("テストTODO");
        dto.setCompleted(false);
        todoService.create(dto);

        UpdateTodoDto updateDto = new UpdateTodoDto();
        updateDto.setId(1L);
        updateDto.setContent("更新されたTODO内容");

        //when&then

        mockMvc.perform(
                        put("/api/todo/update/" + 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateDto))
                )
                .andExpect(status().isOk());

    }

        @Test
    void put_Todo内容更新_存在しないIDなら_404を返す() throws Exception {

        //given
        UpdateTodoDto dto = new UpdateTodoDto();
        dto.setId(999L);
        dto.setContent("更新されたTODO内容");

            //when&then

            mockMvc.perform(
                    put("/api/todo/update/" + 999)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto))

            ).andExpect(status().isNotFound());

        }

    @Test
    void put_Todo状態更新_成功時_200を返す() throws Exception {

        //given
        CreateTodoDto dto = new CreateTodoDto();
        dto.setCompleted(false);
        dto.setContent("テストTODO");
        todoService.create(dto);

        TodoStatusUpdateDto statusDto = new TodoStatusUpdateDto();
        statusDto.setCompleted(true);
        statusDto.setId(1L);

        //when&then

        mockMvc.perform(
                put("/api/todo/update/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statusDto))

        ).andExpect(status().isOk());
    }

    @Test
    void delete_Todo削除_成功時_200を返す() {

        //given

        CreateTodoDto dto = new CreateTodoDto();
        dto.setContent("テストTODO");
        dto.setCompleted(false);
        todoService.create(dto);

        //when&then
        try {
            mockMvc.perform(
                            delete("/api/todo/delete/" + 1)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();


        }

    }

    @Test
    void delete_Todo削除_存在しないIDなら_404を返す() {

        //when&then
        try {
            mockMvc.perform(
                            delete("/api/todo/delete/" + 999)
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            e.printStackTrace();
}}}