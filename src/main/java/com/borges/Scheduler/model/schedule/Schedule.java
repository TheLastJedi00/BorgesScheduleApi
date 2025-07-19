package com.borges.Scheduler.model.schedule;

import com.borges.Scheduler.dto.schedule.SchedulingData;

import com.borges.Scheduler.dto.schedule.SchedulingDataUpdate;
import com.borges.Scheduler.infra.services.ScheduleService;
import com.borges.Scheduler.infra.services.WorkingDays;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity(name = "Agendamentos")
@Table(name = "schedule")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Where(clause = "ativo = true")
@SQLDelete(sql = "UPDATE schedule SET ativo = false WHERE id = ?")
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
        this.endOfService = ScheduleService.calculateEndOfService(data.serviceCode(), data.date());
        this.name = data.name();
        this.phone = data.phone();
        this.service = data.service();
        this.serviceCode = data.serviceCode();
    }

    public void updateInfo(SchedulingDataUpdate data){
        if(data.name() != null){
            this.name = data.name();
        }
        if(data.phone() != null){
            this.phone = data.phone();
        }
        if(data.service() != null){
            this.service = data.service();
        }
        if(data.date() != null){
            this.date = data.date();
            this.endOfService = ScheduleService.calculateEndOfService(this.serviceCode, data.date());
        }
        if(data.dayOfWeek() != null){
            this.dayOfWeek = data.dayOfWeek();
        }
        if (data.date() != null || data.service() != null) {
            this.dayOfWeek = data.dayOfWeek();
            this.endOfService = ScheduleService.calculateEndOfService(this.serviceCode, this.date);
        }
    }
}
