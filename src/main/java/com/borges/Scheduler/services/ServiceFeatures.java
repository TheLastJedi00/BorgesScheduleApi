package com.borges.Scheduler.services;

import java.time.Duration;
import java.time.LocalDateTime;

public class ServiceFeatures {

    public static Duration serviceDuration(int serviceCode){
        if(serviceCode < 3 || serviceCode == 21){
            return Duration.ofMinutes(60);
        } else if (serviceCode >= 22){
            return null;
        }
        return Duration.ofMinutes(120);
    }
}
