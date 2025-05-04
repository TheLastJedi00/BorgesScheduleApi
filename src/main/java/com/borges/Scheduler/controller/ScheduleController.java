package com.borges.Scheduler.controller;

import com.borges.Scheduler.dto.schedule.SchedulingData;
import com.borges.Scheduler.model.schedule.Schedule;
import com.borges.Scheduler.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agenda")
public class ScheduleController {

    @Autowired
    private ScheduleRepository repository;

    @PostMapping
    @Transactional
    public void addSchedule(@RequestBody @Valid SchedulingData data){
        //save Object with parameter "data" in a variable
        var schedule = new Schedule(data);
        //Save Object in a repository
        repository.save(schedule);
    }
}
