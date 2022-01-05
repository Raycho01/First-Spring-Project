package com.rako.spring.controller;

import com.rako.spring.model.Employee;
import com.rako.spring.repository.EmployeeRepository;
import com.rako.spring.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/employees")
public class Controller {
    public EmployeeService employeeService;

    public Controller(EmployeeService employeeService) {
        super();
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getEmployee() {
        return new ResponseEntity<List<Employee>>(employeeService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee){
        return new ResponseEntity<Employee>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
    }

    @GetMapping("{Id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("Id") long id){
        return new ResponseEntity<Employee>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @PostMapping("{Id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable("Id") long id, @RequestBody Employee employee){
        return new ResponseEntity<Employee>(employeeService.updateEmployee(employee, id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteEmployeeById(@PathVariable("id") Long id){
        employeeService.deleteEmployeeById(id);

        return new ResponseEntity<String>("Employee deleted successfully!", HttpStatus.OK);
    }
}
