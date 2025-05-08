package com.leavemanagement.controller;



import com.leavemanagement.dto.EmployeeDto;
import com.leavemanagement.model.Employee; // Use Employee for creation payload as it's simple
import com.leavemanagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody Employee employee) {
        if(employee.getLeaveBalance() == 0) { // Default if not provided
            employee.setLeaveBalance(20); // Example default leave balance
        }
        return new ResponseEntity<>(employeeService.createEmployee(employee), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(employeeService.getEmployeeById(id));
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
