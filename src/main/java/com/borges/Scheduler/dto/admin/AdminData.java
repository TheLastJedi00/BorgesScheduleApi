package com.borges.Scheduler.dto.admin;

import com.borges.Scheduler.model.admin.Admin;
import jakarta.validation.constraints.NotNull;

public record AdminData(
        @NotNull
        String name,
        @NotNull
        String email,
        @NotNull
        String password
) {
    public AdminData(Admin admin) {
        this(admin.getName(), admin.getEmail(), admin.getPassword());
    }
}
