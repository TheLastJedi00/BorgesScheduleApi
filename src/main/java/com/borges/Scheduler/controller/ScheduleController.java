package com.borges.Scheduler.controller;

import com.borges.Scheduler.dto.schedule.SchedulingData;
import com.borges.Scheduler.model.schedule.Schedule;
import com.borges.Scheduler.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/agendamento")
public class ScheduleController {

    @PostMapping
    @Transactional
    public void postSchedule(@RequestBody @Valid SchedulingData data){
        System.out.println(data);
    }
}
