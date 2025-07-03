package com.borges.Scheduler.dto.schedule;

import com.borges.Scheduler.model.schedule.Schedule;
import com.borges.Scheduler.services.WorkingDays;

import java.time.LocalDateTime;

public record ListScheduleData(
        LocalDateTime date,
        LocalDateTime endOfService,
        WorkingDays weekDay,
        String name,
        String phone,
        String service
) {
    public ListScheduleData (Schedule schedule) {
        this(schedule.getDate(), schedule.getEndOfService(), schedule.getDayOfWeek(), schedule.getName(), schedule.getPhone(), schedule.getService());
    }
}
