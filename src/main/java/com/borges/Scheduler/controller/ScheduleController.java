package com.borges.Scheduler.controller;

import com.borges.Scheduler.dto.schedule.ListScheduleData;
import com.borges.Scheduler.dto.schedule.ScheduleDetail;
import com.borges.Scheduler.dto.schedule.SchedulingData;
import com.borges.Scheduler.dto.schedule.SchedulingDataUpdate;
import com.borges.Scheduler.model.schedule.Schedule;
import com.borges.Scheduler.repository.ScheduleRepository;
import com.borges.Scheduler.infra.services.ScheduleService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/agendamento")
public class ScheduleController {
    @Autowired
    private ScheduleRepository repository;
    @Autowired
    private ScheduleService scheduleFeatures;

    @Autowired
    public ScheduleController(ScheduleService scheduleFeatures) {
        this.scheduleFeatures = scheduleFeatures;
    }

    @PostMapping
    @Transactional
    public ResponseEntity postSchedule(@RequestBody @Valid SchedulingData data) {
        Schedule savedSchedule = this.scheduleFeatures.createSchedule(data);
        var uri = ServletUriComponentsBuilder //Build a component
                .fromCurrentRequest() //Builded component from current request
                .path("/{id}") //add a segment in URL
                .buildAndExpand(savedSchedule.getId()) //Change path by ID value
                .toUri(); //convert component in a object URI
        return ResponseEntity.created(uri).body(new ScheduleDetail(savedSchedule));
    }

    @GetMapping
    public ResponseEntity<Page<ListScheduleData>> getSchedule(@RequestParam(name = "date", required = false) LocalDateTime date, @PageableDefault(sort = {"date"}) Pageable pageable) {
        var page = this.scheduleFeatures.listScheduleByDate(date, pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity putSchedule(@RequestBody @Valid SchedulingDataUpdate data){
        var schedule = repository.getReferenceById(data.id());
        schedule.updateInfo(data);
        return ResponseEntity.ok(new ScheduleDetail(schedule));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteSchedule(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}