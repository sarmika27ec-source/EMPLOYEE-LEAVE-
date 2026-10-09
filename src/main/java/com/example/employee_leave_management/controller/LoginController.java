package com.example.employee_leave_management.controller;

import com.example.employee_leave_management.domain.Employee;
import com.example.employee_leave_management.repository.EmployeeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/login")
    public String login(
            @RequestParam String employeename,
            @RequestParam String password,
            HttpSession session) {

        Employee employee =
                employeeRepository.findByEmployeenameAndPassword(
                        employeename,
                        password
                );

        if (employee == null) {
            return "redirect:/login.html?error=true";
        }

        session.setAttribute("employee", employee);

        if ("MANAGER".equalsIgnoreCase(employee.getRole())) {
            return "redirect:/managerdashboard.html";
        }

        if ("EMPLOYEE".equalsIgnoreCase(employee.getRole())) {
            return "redirect:/employeedashboard.html?employeeId="
                    + employee.getId()
                    + "&employeeName="
                    + employee.getEmployeename();
        }

        return "redirect:/login.html?error=role";
    }
}