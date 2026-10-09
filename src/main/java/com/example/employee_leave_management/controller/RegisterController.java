
package com.example.employee_leave_management.controller;

import com.example.employee_leave_management.domain.Employee;
import com.example.employee_leave_management.domain.LeaveBalance;
import com.example.employee_leave_management.domain.LeaveType;
import com.example.employee_leave_management.repository.EmployeeRepository;
import com.example.employee_leave_management.repository.LeaveBalanceRepository;
import com.example.employee_leave_management.repository.LeaveTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class RegisterController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "forward:/register.html";
    }

    @PostMapping("/register")
    public String registerEmployee(@ModelAttribute Employee employee) {

        if (employee.getRole() == null ||
                employee.getRole().isEmpty()) {
            employee.setRole("EMPLOYEE");
        }

        if (employee.getAttendance() == null) {
            employee.setAttendance(0);
        }

        // Save the new employee first
        Employee savedEmployee = employeeRepository.save(employee);

        // Create leave balances for employees only
        if ("EMPLOYEE".equalsIgnoreCase(savedEmployee.getRole())) {

            for (LeaveType type : leaveTypeRepository.findAll()) {

                String name = type.getName().trim()
                        .toLowerCase();

                int days;

                switch (name) {
                    case "casual leave":
                        days = 12;
                        break;

                    case "sick leave":
                        days = 10;
                        break;

                    case "earned leave":
                        days = 15;
                        break;

                    default:
                        continue;
                }

                LeaveBalance balance = new LeaveBalance();

                balance.setEmployeeId(savedEmployee.getId());
                balance.setLeaveTypeId(type.getId());
                balance.setAvailableDays(days);

                leaveBalanceRepository.save(balance);
            }
        }

        return "redirect:/login.html";
    }
}