
package com.nt.employeerestapi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nt.employeerestapi.dto.EmployeeDTO;
import com.nt.employeerestapi.entity.Employee;
import com.nt.employeerestapi.exception.EmployeeNotFoundException;
import com.nt.employeerestapi.repository.EmployeeReository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeService {

    private final EmployeeReository empRepo;

    public EmployeeService(EmployeeReository empRepo) {
        super();
        this.empRepo = empRepo;
    }

    public Employee saveEmp(Employee emp) {

        log.info("Saving employee: {}", emp.getName());

        Employee savedEmployee = empRepo.save(emp);

        log.info("Employee saved successfully with id: {}", savedEmployee.getId());

        return savedEmployee;
    }

    public List<Employee> getAllEmployees() {
        return empRepo.findAll();
    }

    public Employee getEmployeeById(Long id) {

        log.info("Searching employee with id: {}", id);

        Employee employee = empRepo.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee not found with id: " + id));

        log.info("Employee found with id: {}", id);

        return employee;
    }

    public void deleteEmployee(Long id) {
    	log.info("Deleting employee with id: {}",id);
        empRepo.deleteById(id);
        log.info("Employee deleted succesfully with id: {}",id);
    }
    
    @Transactional
    public Employee updateEmployee(Long id, Employee emp) {
        emp.setId(id);
        return empRepo.save(emp);
    }

    public Employee convertToEntity(EmployeeDTO dto) {

        Employee emp = new Employee();

        emp.setName(dto.getName());
        emp.setEmail(dto.getEmail());
        emp.setDepartment(dto.getDepartment());
        emp.setSalary(dto.getSalary());

        return emp;
    }

    public EmployeeDTO convertToDTO(Employee emp) {

        EmployeeDTO dto = new EmployeeDTO();

        dto.setName(emp.getName());
        dto.setEmail(emp.getEmail());
        dto.setDepartment(emp.getDepartment());
        dto.setSalary(emp.getSalary());

        return dto;
    }

    // Normal search
    public List<Employee> searchByName(String name) {
        return empRepo.findByName(name);
    }

    // Search + Pagination
    public Page<Employee> searchByName(String name, Pageable pageable) {
        return empRepo.findByName(name, pageable);
    }

    // Pagination
    public Page<Employee> getEmployees(Pageable pageable) {
        return empRepo.findAll(pageable);
    }

    // Sorting
    public List<Employee> sortEmployees(String field, String direction) {

        Sort sort;

        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(field).descending();
        } else {
            sort = Sort.by(field).ascending();
        }

        return empRepo.findAll(sort);
    }
}


