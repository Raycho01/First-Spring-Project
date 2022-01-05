package com.rako.spring.service;

import com.rako.spring.model.Employee;

import java.util.List;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    List<Employee> findAll();

    Employee getEmployeeById(long id);

    Employee updateEmployee(Employee employee, Long id);

    void deleteEmployeeById(long id);

}