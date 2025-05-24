package com.borges.Scheduler.dto.schedule;

import java.time.LocalDateTime;

public record SchedulingData(
        int day,
        int hour,
        int month,
        String name,
        String phone,
        String service,
        int serviceCode) {
}
