package com.borges.Scheduler.controller;

import com.borges.Scheduler.dto.admin.AdminData;
import com.borges.Scheduler.model.admin.Admin;
import com.borges.Scheduler.repository.AdminRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRepository repository;

    @PostMapping
    @Transactional
    public void admin(@RequestBody @Valid AdminData data){
        var admin = new Admin(data);
        repository.save(admin);
    }
}
