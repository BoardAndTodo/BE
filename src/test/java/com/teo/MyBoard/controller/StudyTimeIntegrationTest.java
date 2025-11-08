package com.teo.MyBoard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teo.MyBoard.service.StudyTimeService;
import com.teo.MyBoard.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StudyTimeIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudyTimeService studyTimeService;


    @Test
    void post_공부시간저장_성공하면_200반환() throws Exception {


        //given

        LocalDate date = LocalDate.now();
        int time = 120;
        studyTimeService.save(date,time);

        //when
        mockMvc.perform(
                        post("/api/studyTime/save")
                                .param("date", date.toString())
                                .param("time", String.valueOf(time))

                )
                .andExpect(status().isOk());
    }

    @Test
    void post_공부시간저장_time없으면_400반환()  throws Exception{

        //given
        LocalDate date = LocalDate.of(2025, 11, 07);

        //when & then

        mockMvc.perform(
                post("/api/studyTime/save")
                        .param("date", date.toString())
        ).andExpect(status().isBadRequest());

    }

}
