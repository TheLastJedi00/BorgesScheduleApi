package com.borges.Scheduler.dto.schedule;

import com.borges.Scheduler.infra.services.WorkingDays;
import java.time.LocalDateTime;

public record SchedulingData(
        LocalDateTime date,
        WorkingDays dayOfWeek,
        String name,
        String phone,
        String service,
        int serviceCode) {
}
