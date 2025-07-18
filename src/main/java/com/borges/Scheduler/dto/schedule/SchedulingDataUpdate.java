package com.borges.Scheduler.dto.schedule;

import com.borges.Scheduler.infra.services.WorkingDays;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SchedulingDataUpdate(
        @NotNull
        Long id,
        LocalDateTime date,
        WorkingDays dayOfWeek,
        String name,
        String phone,
        String service
) {
}
