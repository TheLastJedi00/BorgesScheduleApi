package com.borges.Scheduler.dto.schedule;

import com.borges.Scheduler.model.schedule.Schedule;
import com.borges.Scheduler.infra.services.WorkingDays;

import java.time.LocalDateTime;

public record ListScheduleData(
        Long id,
        LocalDateTime date,
        LocalDateTime endOfService,
        WorkingDays dayOfWeek,
        String name,
        String phone,
        String service
) {
    public ListScheduleData (Schedule schedule) {
        this(schedule.getId(), schedule.getDate(), schedule.getEndOfService(), schedule.getDayOfWeek(), schedule.getName(), schedule.getPhone(), schedule.getService());
    }
}
