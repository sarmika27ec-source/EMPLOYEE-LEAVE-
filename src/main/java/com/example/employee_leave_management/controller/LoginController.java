package com.example.employee_leave_management.controller;

import com.example.employee_leave_management.domain.Employee;
import com.example.employee_leave_management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/login")
    public String showLoginPage() {
        return "forward:/login.html";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String employeename,
            @RequestParam String password) {

        Employee employee =
                employeeRepository.findByEmployeenameAndPassword(
                        employeename,
                        password
                );

        if (employee != null) {

            if ("ADMIN".equalsIgnoreCase(employee.getRole())) {
                return "redirect:/admindashboard.html?employeeId="
                        + employee.getId()
                        + "&employeeName="
                        + employee.getEmployeename();
            }

            if ("MANAGER".equalsIgnoreCase(employee.getRole())) {
                return "redirect:/managerdashboard.html?employeeId="
                        + employee.getId()
                        + "&employeeName="
                        + employee.getEmployeename();
            }

            return "redirect:/employeedashboard.html?employeeId="
                    + employee.getId()
                    + "&employeeName="
                    + employee.getEmployeename();
        }

        return "redirect:/login.html?error=true";
    }
}