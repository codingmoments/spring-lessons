package com.ucp.example.controller;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ucp.example.domain.Employee;
import com.ucp.example.service.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

  @Autowired
  private EmployeeService employeeService;

  @PostMapping("/bulk")
  public void createEmployees() {
    LOGGER.info("creating employees in bulk ...");

    Employee employee = new Employee();
    employee.setEmployeeId(Instant.now().getEpochSecond());
    employee.setName("Johny");

    LOGGER.info("saving first employee ...");
    employeeService.createEmployee(employee);

    try {
      LOGGER.info("waiting for 60 seconds ...");
      Thread.sleep(60 * 1000);
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    employee = new Employee();
    employee.setEmployeeId(Instant.now().getEpochSecond());
    employee.setName("Jimmy");

    LOGGER.info("saving second employee ...");
    employeeService.createEmployee(employee);
  }
}
