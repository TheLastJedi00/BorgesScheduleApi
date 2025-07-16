package com.borges.Scheduler.infra.exceptions;

public class ScheduleException extends RuntimeException{
    public ScheduleException(String message){
        super(message);
    }
}
