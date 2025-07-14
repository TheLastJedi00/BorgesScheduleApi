package com.borges.Scheduler.dto.admin;

import jakarta.validation.constraints.NotNull;

public record AdminLogin(
        @NotNull
        String email,
        @NotNull
        String password
) {
}
