package com.borges.Scheduler.dto.schedule;

import com.borges.Scheduler.services.WorkingDays;

import java.time.LocalDateTime;

public record ListScheduleData(
        LocalDateTime date,
        WorkingDays weekDay,
        String name,
        String phone,
        String service
) {
}
