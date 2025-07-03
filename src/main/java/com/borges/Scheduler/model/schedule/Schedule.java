package com.borges.Scheduler.model.schedule;

import com.borges.Scheduler.dto.schedule.SchedulingData;

import com.borges.Scheduler.services.ScheduleFeatures;
import com.borges.Scheduler.services.WorkingDays;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Agendamentos")
@Table(name = "schedule")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "agendamento")
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private WorkingDays dayOfWeek;
    @Column(name = "fim_de_agendamento")
    private LocalDateTime endOfService;
    @Column(name = "nome")
    private String name;
    @Column(name = "telefone")
    private String phone;
    @Column(name = "servico")
    private String service;
    @Column(name = "service_code")
    private int serviceCode;

    public Schedule(SchedulingData data) {
        this.date = data.date();
        this.dayOfWeek = data.dayOfWeek();
        this.endOfService = ScheduleFeatures.calculateEndOfService(data.serviceCode(), data.date());
        this.name = data.name();
        this.phone = data.phone();
        this.service = data.service();
        this.serviceCode = data.serviceCode();
    }
}
