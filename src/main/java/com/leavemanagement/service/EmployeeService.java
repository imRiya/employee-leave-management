package com.leavemanagement.service;

import com.leavemanagement.dto.EmployeeDto;
import com.leavemanagement.model.Employee;
import com.leavemanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeDto createEmployee(Employee employee) {
        // You might want to add validation here (e.g., if email already exists)
        Employee savedEmployee = employeeRepository.save(employee);
        return mapToEmployeeDto(savedEmployee);
    }

    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
        return mapToEmployeeDto(employee);
    }

    // Helper method to map Entity to DTO
    private EmployeeDto mapToEmployeeDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getRole(),
                employee.getLeaveBalance()
        );
    }
}
