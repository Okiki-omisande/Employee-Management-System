package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.entity.Employee;
import com.example.employeemanagementsystem.exception.ResourceNotFoundException;
import com.example.employeemanagementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("employee not found for id: " + employeeId));
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping("employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        return this.employeeRepository.save(employee);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId, @RequestBody Employee employee) throws ResourceNotFoundException {
        Employee employeeUpdate = employeeRepository.findById(employeeId).orElseThrow(()-> new ResourceNotFoundException("employee not found for id: " +employeeId));
        employeeUpdate.setFirstname(employee.getFirstname());
        employeeUpdate.setLastname(employee.getLastname());
        employeeUpdate.setEmail(employee.getEmail());
        return ResponseEntity.ok().body(this.employeeRepository.save(employee));
    }
    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("employee not found for id: "+ id));
        employeeRepository.delete(employee);

    }
}
