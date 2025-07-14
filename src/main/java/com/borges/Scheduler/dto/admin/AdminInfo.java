package com.borges.Scheduler.dto.admin;
import com.borges.Scheduler.model.admin.Admin;
import jakarta.validation.constraints.NotNull;

public record AdminInfo(
        @NotNull
        Long id,
        @NotNull
        String name,
        @NotNull
        String email
) {
    public AdminInfo(Admin admin) {
        this(admin.getId(), admin.getName(), admin.getEmail());
    }
}
