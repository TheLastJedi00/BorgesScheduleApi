package com.borges.Scheduler.dto.admin;

import jakarta.validation.constraints.NotNull;

public record AdminData(
        @NotNull
        String name,
        @NotNull
        String email,
        @NotNull
        String password
){

}
