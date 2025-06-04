package com.borges.Scheduler.controller;

import com.borges.Scheduler.dto.schedule.ScheduleDetail;
import com.borges.Scheduler.dto.schedule.SchedulingData;
import com.borges.Scheduler.model.schedule.Schedule;
import com.borges.Scheduler.repository.ScheduleRepository;
import com.borges.Scheduler.services.ServiceFeatures;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/agendamento")
public class ScheduleController {

    @Autowired
    private ScheduleRepository repository;
    @Autowired
    private ServiceFeatures serviceFeatures;
    @Autowired
    public ScheduleController(ServiceFeatures serviceFeatures) {
        this.serviceFeatures = serviceFeatures;
    }

    @PostMapping
    @Transactional
    public ResponseEntity postSchedule(@RequestBody @Valid SchedulingData data) {
        Schedule savedSchedule = this.serviceFeatures.createSchedule(data);
        var uri = ServletUriComponentsBuilder //Build a component
                .fromCurrentRequest() //Builded component from current request
                .path("/{id}") //add a segment in URL
                .buildAndExpand(savedSchedule.getId()) //Change path by ID value
                .toUri(); //convert component in a object URI
        return ResponseEntity.created(uri).body(new ScheduleDetail(savedSchedule));
    }
}
