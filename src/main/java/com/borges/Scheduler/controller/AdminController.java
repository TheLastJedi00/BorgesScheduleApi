package com.borges.Scheduler.controller;

import com.borges.Scheduler.dto.admin.AdminData;
import com.borges.Scheduler.dto.admin.AdminInfo;
import com.borges.Scheduler.model.admin.Admin;
import com.borges.Scheduler.repository.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRepository repository;


    @PostMapping
    @Transactional
    public ResponseEntity createAdmin(@RequestBody AdminData data){
        Admin save = repository.save(new Admin(data));
        var uri = ServletUriComponentsBuilder //Build a component
                .fromCurrentRequest() //Builded component from current request
                .path("/{id}") //add a segment in URL
                .buildAndExpand(save) //Change path by ID value
                .toUri(); //convert component in a object URI
        return ResponseEntity.created(uri).body(new AdminInfo(save));
    }
}
