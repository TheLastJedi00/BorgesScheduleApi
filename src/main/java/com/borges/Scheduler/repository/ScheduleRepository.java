package com.borges.Scheduler.repository;

import com.borges.Scheduler.model.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
