package com.borges.Scheduler.repository;

import com.borges.Scheduler.model.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    UserDetails findByEmail(String email);
}
