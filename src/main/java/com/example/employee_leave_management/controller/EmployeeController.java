package com.example.employee_leave_management.controller;

import com.example.employee_leave_management.domain.Employee;
import com.example.employee_leave_management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/api/register")
    public Employee register(@RequestBody Employee employee) {

        employee.setRole("EMPLOYEE");

        if (employee.getAttendance() == null) {
            employee.setAttendance(0);
        }

        employee.setActive(true);

        return employeeRepository.save(employee);
    }

    @PostMapping("/api/login")
    public Employee login(@RequestBody Employee loginEmployee) {

        return employeeRepository
                .findByEmailAndPassword(
                        loginEmployee.getEmail(),
                        loginEmployee.getPassword()
                )
                .orElse(null);
    }

    @GetMapping("/api/employees")
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/api/employee/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @PutMapping("/api/employee/{id}")
    public Employee updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee updatedEmployee) {

        Employee employee =
                employeeRepository.findById(id).orElse(null);

        if (employee == null) {
            return null;
        }

        employee.setEmployeename(updatedEmployee.getEmployeename());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setDepartment(updatedEmployee.getDepartment());

        if (updatedEmployee.getPassword() != null &&
                !updatedEmployee.getPassword().isEmpty()) {

            employee.setPassword(updatedEmployee.getPassword());
        }

        return employeeRepository.save(employee);
    }
}
