package com.borges.Scheduler.model.admin;

import com.borges.Scheduler.dto.admin.AdminData;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "admin")
@Entity(name = "Admin")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Admin {
    private Long id;
    private String name;
    private String email;
    private String password;

    public Admin(AdminData data) {
        this.name = data.name();
        this.email = data.email();
        this.password = data.password();
    }
}
