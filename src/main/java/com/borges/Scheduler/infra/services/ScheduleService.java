package com.borges.Scheduler.infra.services;

import com.borges.Scheduler.dto.schedule.ListScheduleData;
import com.borges.Scheduler.dto.schedule.SchedulingData;
import com.borges.Scheduler.model.schedule.Schedule;
import com.borges.Scheduler.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.SchedulingException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    //calculate end of service based in respective service code
    public static LocalDateTime calculateEndOfService(int serviceCode, LocalDateTime startService) {
        // Service 1, 2 and 21 have 59min of duration
        if (serviceCode == 1 || serviceCode == 2 || serviceCode == 21) {
            return startService.plusMinutes(59);
        } else {
            return startService.plusMinutes(119);
        }
    }

    public static boolean isOpeningHours(WorkingDays workingDays, LocalDateTime dateTime) {
        int time = dateTime.getHour();
        boolean timeIsAfter = time > 18;
        boolean interval = time == 12;
        boolean timeIsBefore = time < 14;
        //Days with exceptions
        if (workingDays == WorkingDays.DOMINGO
                || (workingDays == WorkingDays.SABADO && (time < 8 || timeIsAfter || interval))
                || (workingDays == WorkingDays.QUARTA && (time < 15 || timeIsAfter))) {
            throw new SchedulingException("Não funcionamos nesse horário ou dia");
        } else if (workingDays != WorkingDays.SABADO && timeIsBefore || timeIsAfter) {
            throw new SchedulingException("Fora do horário de funcionamento");
        }
        return true;
    }

    public boolean noConflict(LocalDateTime requestedHour, LocalDateTime requestedEndHour, LocalDateTime foundStartHour, LocalDateTime foundEndHour, WorkingDays workingDay) {
        System.out.println("Iniciando checagem de conflitos");

        boolean directStartConflict = requestedHour.isEqual(foundStartHour);
        boolean startBetweenSchedule = requestedHour.isAfter(foundStartHour) && requestedHour.isBefore(foundEndHour);
        boolean endBetweenConflict = requestedEndHour.isAfter(foundStartHour) && requestedHour.isBefore(foundEndHour);

        if (directStartConflict || startBetweenSchedule || endBetweenConflict) {
            throw new SchedulingException("Já existe um agendamento nesse horário");
        } else {
            System.out.println("Nenhum conflito encontrado");
            return true;
        }
    }

    public boolean isSchedulingTimeAvailable(LocalDateTime requestedScheduleDateTime, LocalDateTime requestedScheduleEndDate, WorkingDays workingDays) {
        LocalDateTime startOfDay = requestedScheduleDateTime.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        List<Schedule> schedulesByDate = scheduleRepository.findByDateBetween(startOfDay, endOfDay);
        //Check if requested hour is a working hour
        isOpeningHours(workingDays, requestedScheduleDateTime);
        //Check all schedules in requested date
        for (Schedule schedule : schedulesByDate) {
            boolean hasConflictsFound = !noConflict(requestedScheduleDateTime, requestedScheduleEndDate, schedule.getDate(), calculateEndOfService(schedule.getServiceCode(), schedule.getDate()), workingDays);
            if (hasConflictsFound) {
                throw new SchedulingException("Conflito de horários");
            }
        }
        return true;
    }

    @Transactional
    public Schedule createSchedule(SchedulingData schedulingData) {
        var scheduleObject = new Schedule(schedulingData);
        LocalDateTime schedulingDate = scheduleObject.getDate();
        LocalDateTime schedulingEndDate = scheduleObject.getEndOfService();
        WorkingDays schedulingWeekDay = scheduleObject.getDayOfWeek();

        System.out.println("Requisição recebida, realizando checagens");
        try {
            if (isSchedulingTimeAvailable(schedulingDate, schedulingEndDate, schedulingWeekDay)) {
                System.out.println("Verificação de conflitos concluída. Salvando agendamento");
                return scheduleRepository.save(scheduleObject);
            } else {
                log.warn("isSchedulingTimeAvailable = false: Fail by Exception");
                throw new SchedulingException("Não disponível por exceção desconhecida");
            }
        } catch(SchedulingException error) {
            throw new SchedulingException("Agendamento não realizado: " + error.getMessage());
        }
    }

    public Page<ListScheduleData> listScheduleByDate(LocalDateTime date, Pageable pageable){
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        return scheduleRepository.findByDateBetween(startOfDay, endOfDay, pageable);
    }

    public Page<ListScheduleData> listSchedulesOfWeek(LocalDateTime monday, LocalDateTime endOfWeek, Pageable pageable){
        LocalDateTime startOfWeek = monday.toLocalDate().atStartOfDay();

        return scheduleRepository.findByDateBetween(startOfWeek, endOfWeek, pageable);
    }

}
