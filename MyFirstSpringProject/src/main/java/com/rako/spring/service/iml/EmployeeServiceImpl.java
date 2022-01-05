package com.rako.spring.service.iml;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.rako.spring.exception.ResourceNotFoundException;
import com.rako.spring.model.Employee;
import com.rako.spring.repository.EmployeeRepository;
import com.rako.spring.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(long id) {

        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isPresent()){
            return employee.get();
        }
        else {
            throw new ResourceNotFoundException("Employee", "Id", id);
        }

    }

    @Override
    public Employee updateEmployee(Employee employee, Long id) {

        Employee existingEmployee = employeeRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Employee", "Id", id) );

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());

        return employeeRepository.save(existingEmployee);

    }

    @Override
    public void deleteEmployeeById(long id) {

        if (employeeRepository.findById(id).isPresent()){
            employeeRepository.deleteById(id);
        }
        else {
            throw new ResourceNotFoundException("Employee", "Id", id);
        }

    }

}
