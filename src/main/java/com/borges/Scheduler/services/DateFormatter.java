package com.borges.Scheduler.services;

import java.time.LocalDateTime;
import java.time.Month;

public class DateFormatter {
    public static LocalDateTime formatter(int day, int month, int hour){
        return LocalDateTime.of(LocalDateTime.now().getYear(), Month.of(month), day, hour, 0);
    }
}
