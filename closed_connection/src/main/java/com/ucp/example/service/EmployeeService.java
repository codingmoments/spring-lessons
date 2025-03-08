package com.ucp.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ucp.example.domain.Employee;
import com.ucp.example.repository.EmployeeRepository;

@Service
public class EmployeeService {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Transactional
  public void createEmployee(Employee employee) {
    employeeRepository.save(employee);
  }
}
