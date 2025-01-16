package com.permansys.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.permansys.entity.Employee;
import com.permansys.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> getAllUsers() {
		return employeeRepository.findAll();
	}

	public Employee getUserByAccount(String account) {
		return employeeRepository.findById(account).orElse(null);
	}

    public List getEmployeeCase1(String account, String department, String team, String status, LocalDate hireDate) {
        return (List) employeeRepository.queryCase1(account, department, team, status, hireDate);
    }

    public Optional<Employee> validateEmployee(String account, String password) {
        return employeeRepository.findByUsernameAndPassword(account, password);
    }
}
