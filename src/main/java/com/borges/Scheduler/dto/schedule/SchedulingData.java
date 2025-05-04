package com.borges.Scheduler.dto.schedule;

import com.borges.Scheduler.model.ServiceList;
import java.time.LocalDateTime;

public record SchedulingData(
        String name,
        LocalDateTime hour,
        LocalDateTime scheduleDate,
        ServiceList serviceList,
        String phone) {
}
