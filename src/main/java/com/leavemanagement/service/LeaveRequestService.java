package com.leavemanagement.service;

import com.leavemanagement.dto.LeaveRequestCreateDto;
import com.leavemanagement.dto.LeaveRequestDto;
import com.leavemanagement.model.Employee;
import com.leavemanagement.model.LeaveRequest;
import com.leavemanagement.repository.EmployeeRepository;
import com.leavemanagement.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public LeaveRequestDto applyForLeave(LeaveRequestCreateDto createDto) {
        Employee employee = employeeRepository.findById(createDto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + createDto.getEmployeeId()));

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployee(employee);
        leaveRequest.setStartDate(createDto.getStartDate());
        leaveRequest.setEndDate(createDto.getEndDate());
        leaveRequest.setReason(createDto.getReason());
        leaveRequest.setLeaveType(createDto.getLeaveType());
        leaveRequest.setStatus("PENDING"); // Default status

        LeaveRequest savedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        return mapToLeaveRequestDto(savedLeaveRequest);
    }

    public List<LeaveRequestDto> getLeaveRequestsByEmployee(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new EntityNotFoundException("Employee not found with id: " + employeeId);
        }
        return leaveRequestRepository.findByEmployeeId(employeeId).stream()
                .map(this::mapToLeaveRequestDto)
                .collect(Collectors.toList());
    }

    public List<LeaveRequestDto> getPendingLeaveRequests() {
        return leaveRequestRepository.findByStatus("PENDING").stream()
                .map(this::mapToLeaveRequestDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public LeaveRequestDto approveLeaveRequest(Long leaveId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new EntityNotFoundException("Leave request not found with id: " + leaveId));

        if (!"PENDING".equals(leaveRequest.getStatus())) {
            throw new IllegalStateException("Leave request is not in PENDING state.");
        }

        Employee employee = leaveRequest.getEmployee();
        long daysRequested = ChronoUnit.DAYS.between(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1;

        if (employee.getLeaveBalance() < daysRequested) {
            System.out.println("Warning: Employee " + employee.getName() + " has insufficient leave balance for this request.");
        }

        employee.setLeaveBalance(employee.getLeaveBalance() - (int) daysRequested);
        employeeRepository.save(employee); // Save updated employee balance

        leaveRequest.setStatus("APPROVED");
        LeaveRequest updatedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        return mapToLeaveRequestDto(updatedLeaveRequest);
    }

    @Transactional
    public LeaveRequestDto rejectLeaveRequest(Long leaveId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new EntityNotFoundException("Leave request not found with id: " + leaveId));

        if (!"PENDING".equals(leaveRequest.getStatus())) {
            throw new IllegalStateException("Leave request is not in PENDING state.");
        }

        leaveRequest.setStatus("REJECTED");
        LeaveRequest updatedLeaveRequest = leaveRequestRepository.save(leaveRequest);
        return mapToLeaveRequestDto(updatedLeaveRequest);
    }

    private LeaveRequestDto mapToLeaveRequestDto(LeaveRequest leaveRequest) {
        return new LeaveRequestDto(
                leaveRequest.getId(),
                leaveRequest.getEmployee().getId(),
                leaveRequest.getEmployee().getName(),
                leaveRequest.getStartDate(),
                leaveRequest.getEndDate(),
                leaveRequest.getReason(),
                leaveRequest.getLeaveType(),
                leaveRequest.getStatus()
        );
    }
}