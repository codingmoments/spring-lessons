package com.ucp.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ucp.example.domain.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
