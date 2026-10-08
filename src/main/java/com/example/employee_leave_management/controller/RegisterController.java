package com.example.employee_leave_management.controller;

import com.example.employee_leave_management.domain.Employee;
import com.example.employee_leave_management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class RegisterController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "forward:/register.html";
    }

    @PostMapping("/register")
    public String registerEmployee(@ModelAttribute Employee employee) {

        if (employee.getRole() == null || employee.getRole().isEmpty()) {
            employee.setRole("EMPLOYEE");
        }

        if (employee.getAttendance() == null) {
            employee.setAttendance(0);
        }

        employeeRepository.save(employee);

        return "redirect:/login.html";
    }
}