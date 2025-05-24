package com.borges.Scheduler.model.schedule;

import com.borges.Scheduler.dto.schedule.SchedulingData;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @NotNull
    private int day;
    @NotNull
    private int hour;
    @NotNull
    private int month;
    @NotNull
    private String name;
    @NotNull
    private String phone;
    @NotNull
    private String service;
    @NotNull
    private int serviceCode;

    public Schedule(int day, int hour, int month, String name, String phone, String service, int serviceCode) {
        this.day = day;
        this.hour = hour;
        this.month = month;
        this.name = name;
        this.phone = phone;
        this.service = service;
        this.serviceCode = serviceCode;
    }
}
