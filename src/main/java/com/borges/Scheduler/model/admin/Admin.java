package com.borges.Scheduler.model.admin;

import com.borges.Scheduler.dto.admin.AdminData;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "admin")
@Entity(name = "Admin")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;

    public Admin(AdminData data) {
        this.name = data.name();
        this.email = data.email();
        this.password = data.password();
    }
}
