package com.nt.employeerestapi.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.employeerestapi.entity.Employee;

public interface EmployeeReository extends JpaRepository<Employee, Long> {

    List<Employee> findByName(String name);

    Page<Employee> findByName(String name, Pageable pageable);
}