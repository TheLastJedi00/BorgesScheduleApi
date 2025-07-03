package com.borges.Scheduler.repository;

import com.borges.Scheduler.dto.schedule.ListScheduleData;
import com.borges.Scheduler.model.schedule.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository <Schedule, Long> {

    List<Schedule> findAll();

    List<Schedule> findByDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    Page<ListScheduleData> findByDateBetween(LocalDateTime startOfDay, LocalDateTime endOfDay, Pageable pageable);

}
