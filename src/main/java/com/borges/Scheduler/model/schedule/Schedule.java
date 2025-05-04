package com.borges.Scheduler.model.schedule;

import com.borges.Scheduler.dto.schedule.SchedulingData;
import com.borges.Scheduler.model.ServiceList;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @NotNull
    private String name;
    @NotNull
    private LocalDateTime hour;
    @NotNull
    private LocalDateTime schelduleDate;
    @NotNull
    private ServiceList serviceList;
    @NotNull
    private String phone;

    public Schedule (SchedulingData data) {
        this.name = data.name();
        this.hour = data.hour();
        this.schelduleDate = schelduleDate;
        this.phone = data.phone();
    }
}
