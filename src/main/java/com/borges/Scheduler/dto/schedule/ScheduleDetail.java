package com.borges.Scheduler.dto.schedule;

import com.borges.Scheduler.model.schedule.Schedule;
import java.time.LocalDateTime;

public record ScheduleDetail(
        Long id,
        LocalDateTime date,
        String name,
        String phone,
        String service) {

    public ScheduleDetail(Schedule schedule) {
        this(
                schedule.getId(),
                schedule.getDate(),
                schedule.getName(),
                schedule.getPhone(),
                schedule.getService()
        );
    }
}
