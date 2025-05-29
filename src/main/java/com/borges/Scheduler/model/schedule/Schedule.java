package com.borges.Scheduler.model.schedule;

import com.borges.Scheduler.dto.schedule.SchedulingData;

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
        this.name = data.name();
        this.phone = data.phone();
        this.service = data.service();
        this.serviceCode = data.serviceCode();
    }
}
