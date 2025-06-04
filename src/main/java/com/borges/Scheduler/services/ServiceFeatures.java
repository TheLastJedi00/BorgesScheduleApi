package com.borges.Scheduler.services;

import com.borges.Scheduler.dto.schedule.ScheduleDetail;
import com.borges.Scheduler.dto.schedule.SchedulingData;
import com.borges.Scheduler.model.schedule.Schedule;
import com.borges.Scheduler.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.SchedulingException;
import org.springframework.stereotype.Service;

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

    public static boolean isOpeningHours(WorkingDays workingDays, LocalDateTime dateTime){
        int time = dateTime.getHour();
        boolean timeIsAfter = time > 18;
        boolean interval = time == 12;
        boolean timeIsBefore = time == 14;
        //Days with exceptions
        if(workingDays == WorkingDays.DOMINGO || (workingDays == WorkingDays.SABADO && (time < 8 || timeIsAfter || interval)) || (workingDays == WorkingDays.QUARTA && (time < 15 || timeIsAfter))){
            return false;
        }
        //Normal days
        else if (timeIsBefore || timeIsAfter || interval){
            return false;
        }
        return true;
    }

    public boolean isSchedulingTimeAvailable(LocalDateTime requestedScheduleDateTime){
        List<Schedule> schedulesOnDatabase = scheduleRepository.findByDate(requestedScheduleDateTime);
        LocalDateTime scheduleDateTime = schedulesOnDatabase.getFirst().getDate();
        LocalDateTime scheduleEndDateTime = schedulesOnDatabase.getFirst().getEndOfService();
        //If new schedule is equal or is between a current schedule
        if((requestedScheduleDateTime.isBefore(scheduleEndDateTime) && requestedScheduleDateTime.isAfter(scheduleDateTime)) || requestedScheduleDateTime.isEqual(scheduleDateTime)){
            return false;
        }
        return true;
    }

    @Transactional
    public Schedule createSchedule(SchedulingData schedulingData){
        var scheduleObject = new Schedule(schedulingData);
        LocalDateTime schedulingDate = scheduleObject.getDate();
        var saveSchedule = scheduleRepository.save(scheduleObject);

        if(isSchedulingTimeAvailable(schedulingDate)){
            return saveSchedule;
        } else {
            throw new SchedulingException("Esse horário está ocupado");
        }
    }

}
