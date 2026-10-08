package com.example.employee_leave_management.repository;

import com.example.employee_leave_management.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByEmployeenameAndPassword(
            String employeename,
            String password
    );
}