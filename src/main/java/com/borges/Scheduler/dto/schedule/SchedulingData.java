package com.borges.Scheduler.dto.schedule;

import java.time.LocalDateTime;

public record SchedulingData(
        LocalDateTime date,
        String name,
        String phone,
        String service,
        int serviceCode) {
}
