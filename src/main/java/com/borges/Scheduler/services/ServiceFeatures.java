package com.borges.Scheduler.services;

import com.borges.Scheduler.dto.schedule.ScheduleDetail;
import com.borges.Scheduler.dto.schedule.SchedulingData;
import com.borges.Scheduler.model.schedule.Schedule;
import com.borges.Scheduler.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.SchedulingException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceFeatures {

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
        }
        //Normal days (Redundant)
        else if ((workingDays != WorkingDays.SABADO
                && workingDays != WorkingDays.QUARTA) && (timeIsBefore || timeIsAfter)) {
            throw new SchedulingException("Fora do horário de funcionamento");
        }
        return true;
    }

    public boolean noConflict(LocalDateTime requestedHour, LocalDateTime foundStartHour, LocalDateTime foundEndHour, WorkingDays workingDay) {
        System.out.println("Iniciando checagem de conflitos");
        if (!((requestedHour.isEqual(foundStartHour))                                              //if requested hour is not conflict with other scheduling start
                && (requestedHour.isAfter(foundStartHour)) && requestedHour.isBefore(foundEndHour))//and requested hour is not between start and end of other scheduling
                && isOpeningHours(workingDay, requestedHour))                                      //and Opening hour is true
        {
            System.out.println("Conflito não encontrado");
            return true;
        }
        throw new SchedulingException("Já existe um agendamento nesse horário");
    }

    public boolean isSchedulingTimeAvailable(LocalDateTime requestedScheduleDateTime, WorkingDays workingDays) {
        LocalDateTime startOfDay = requestedScheduleDateTime.withHour(0);
        LocalDateTime endOfDay = requestedScheduleDateTime.withHour(23);
        List<Schedule> schedulesByDate = scheduleRepository.findByDateBetween(startOfDay, endOfDay);

        System.out.println("Checando data e hora escolhida");

        if (!schedulesByDate.isEmpty()) {
            System.out.println("Agendamento encontrado na data, checando horário");
            List<Schedule> schedulesByDateTime = scheduleRepository.findByDate(requestedScheduleDateTime);
            //If this DateTime is not empty
            if (!schedulesByDateTime.isEmpty()) {
                System.out.println("Horário encontrado, checando conflitos");
                LocalDateTime schedulesOnHour = schedulesByDateTime.get(0).getDate();
                LocalDateTime endOfSchedulesOnHour = schedulesByDateTime.get(0).getEndOfService();
                //Check conflict with other schedules
                return noConflict(requestedScheduleDateTime, schedulesOnHour, endOfSchedulesOnHour, workingDays);
            }
        }

        throw new SchedulingException("Foi encontrado um agendamento conflitante");
    }

    @Transactional
    public Schedule createSchedule(SchedulingData schedulingData) {
        var scheduleObject = new Schedule(schedulingData);
        LocalDateTime schedulingDate = scheduleObject.getDate();
        WorkingDays schedulingWeekDay = scheduleObject.getDayOfWeek();

        System.out.println("Requisição recebida, realizando checagens");
        if (isSchedulingTimeAvailable(schedulingDate, schedulingWeekDay)) {
            return scheduleRepository.save(scheduleObject);
        } else {
            throw new SchedulingException("Erro na data ou horário escolhido");
        }
    }

}
