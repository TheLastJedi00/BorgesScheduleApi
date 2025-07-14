package com.borges.Scheduler.controller;

import com.borges.Scheduler.dto.admin.AdminLogin;
import com.borges.Scheduler.repository.AdminRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager manager;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid AdminLogin admin) {

        var token = new UsernamePasswordAuthenticationToken(admin.email(), admin.password());
        var authentication = manager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}
