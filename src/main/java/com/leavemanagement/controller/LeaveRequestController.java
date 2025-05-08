package com.leavemanagement.controller;



import com.leavemanagement.dto.LeaveRequestCreateDto;
import com.leavemanagement.dto.LeaveRequestDto;
import com.leavemanagement.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping
    public ResponseEntity<?> applyForLeave(@RequestBody LeaveRequestCreateDto createDto) {
        try {
            LeaveRequestDto leaveRequestDto = leaveRequestService.applyForLeave(createDto);
            return new ResponseEntity<>(leaveRequestDto, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error applying for leave: " + e.getMessage());
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<?> getLeaveRequestsByEmployee(@PathVariable Long employeeId) {
        try {
            List<LeaveRequestDto> leaveRequests = leaveRequestService.getLeaveRequestsByEmployee(employeeId);
            return ResponseEntity.ok(leaveRequests);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<List<LeaveRequestDto>> getPendingLeaveRequests() {
        List<LeaveRequestDto> pendingLeaves = leaveRequestService.getPendingLeaveRequests();
        return ResponseEntity.ok(pendingLeaves);
    }

    @PutMapping("/{leaveId}/approve")
    public ResponseEntity<?> approveLeaveRequest(@PathVariable Long leaveId) {
        try {
            LeaveRequestDto updatedLeaveRequest = leaveRequestService.approveLeaveRequest(leaveId);
            return ResponseEntity.ok(updatedLeaveRequest);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error approving leave: " + e.getMessage());
        }
    }

    @PutMapping("/{leaveId}/reject")
    public ResponseEntity<?> rejectLeaveRequest(@PathVariable Long leaveId) {
        try {
            LeaveRequestDto updatedLeaveRequest = leaveRequestService.rejectLeaveRequest(leaveId);
            return ResponseEntity.ok(updatedLeaveRequest);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error rejecting leave: " + e.getMessage());
        }
    }
}