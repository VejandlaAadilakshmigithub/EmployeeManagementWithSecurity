package com.nt.employeerestapi.controller;

import java.util.List;



import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nt.employeerestapi.dto.EmployeeDTO;
import com.nt.employeerestapi.entity.Employee;
import com.nt.employeerestapi.service.EmployeeService;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.http.ResponseEntity;
@RestController
public class EmployeeController {
	
	private final EmployeeService empServ;

	public EmployeeController(EmployeeService empServ) {
		super();
		this.empServ = empServ;
	}

	@GetMapping("/hello")
	public String hello() {
		return "Employee rest api is working";
	}
	

	@PostMapping("/employees")
	public ResponseEntity<Employee> saveEmployee(@Valid @RequestBody EmployeeDTO dto) {

	    Employee emp = empServ.convertToEntity(dto);
	    Employee savedEmployee = empServ.saveEmp(emp);

	    return ResponseEntity.status(201).body(savedEmployee);
	}
	@GetMapping("/employees1")
	public List<EmployeeDTO> getAllEmployees() {

	    List<Employee> employees = empServ.getAllEmployees();

	    return employees.stream()
	            .map(empServ::convertToDTO)
	            .toList();
	}
	@GetMapping("/employees/{id}")
	public EmployeeDTO getEmployeeById(@PathVariable Long id) {

	    Employee emp = empServ.getEmployeeById(id);

	    return empServ.convertToDTO(emp);
	}
	
	@DeleteMapping("/employees/{id}")
	public String deleteEmployee(@PathVariable Long id) {
		empServ.deleteEmployee(id);
		return "Emp deleted succefully";
	}
	
	@PutMapping("/employees/{id}")
	public Employee  updateEmployee(@PathVariable Long id,
			                           @RequestBody Employee employee) {
		return empServ.updateEmployee(id, employee);
	}
	@GetMapping("/employees/search-page")
	public Page<EmployeeDTO> searchEmployees(
	        @RequestParam String name,
	        Pageable pageable) {

	    Page<Employee> employees = empServ.searchByName(name, pageable);

	    return employees.map(empServ::convertToDTO);
	}
	
	@GetMapping("/employees/page")
	public Page<EmployeeDTO> getEmployees(Pageable pageable) {

	    Page<Employee> employees = empServ.getEmployees(pageable);

	    return employees.map(empServ::convertToDTO);
	}
	@GetMapping("/employees/sort")
	public List<EmployeeDTO> sortEmployees(
	        @RequestParam String field,
	        @RequestParam String direction) {

	    List<Employee> employees = empServ.sortEmployees(field, direction);

	    return employees.stream()
	            .map(empServ::convertToDTO)
	            .toList();
	}
	
	
}
