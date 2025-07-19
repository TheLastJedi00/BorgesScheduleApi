package com.borges.Scheduler.controller;

import com.borges.Scheduler.dto.schedule.ScheduleDetail;
import com.borges.Scheduler.dto.schedule.SchedulingData;
import com.borges.Scheduler.infra.services.ScheduleService;
import com.borges.Scheduler.infra.services.WorkingDays;
import com.borges.Scheduler.model.schedule.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ScheduleControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<SchedulingData> scheduleJson;
    @Autowired
    private JacksonTester<ScheduleDetail> scheduleResponseJson;
    @MockBean
    private ScheduleService scheduleService;

    @Test
    @DisplayName("Must return error 400 when not found")
    void postNotFoundSchedule() throws Exception {
        var response = mvc.perform(post("/agendamento")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Must return error 201 when valid JSON")
    void postValidSchedule() throws Exception {
        var schedulingData = new SchedulingData(
                LocalDateTime.now().plusDays(1).withHour(16),
                WorkingDays.QUARTA,
                "João Silva",
                "11999999999",
                "Corte de Cabelo",
                1
        );
        LocalDateTime date = LocalDateTime.now().plusDays(1).withHour(16);

        when(scheduleService.createSchedule(any(SchedulingData.class)))
                .thenReturn(new Schedule(schedulingData));

        var response = mvc.perform(post("/agendamento").contentType(MediaType.APPLICATION_JSON)
                        .content(scheduleJson.write(schedulingData).getJson()))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var responseJson = scheduleResponseJson.write(
                        new ScheduleDetail(
                                null, date, "João Silva", "11999999999", "Corte de Cabelo", 1))
                .getJson();

        assertThat(response.getContentAsString()).isEqualTo(responseJson);
    }
}