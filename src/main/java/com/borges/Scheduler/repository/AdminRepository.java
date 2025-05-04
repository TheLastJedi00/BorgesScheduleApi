package com.borges.Scheduler.repository;

import com.borges.Scheduler.model.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
