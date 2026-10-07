package com.example.employee_leave_management.repository;

import com.example.employee_leave_management.domain.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
}