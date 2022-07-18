package com.epam.springboot.graphql.services;

import com.epam.springboot.graphql.entity.Department;
import com.epam.springboot.graphql.entity.Employee;
import com.epam.springboot.graphql.repository.DepartmentRepository;
import com.epam.springboot.graphql.repository.EmployeeRepository;
import graphql.GraphQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Integer id) {
        return employeeRepository.getById(id);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }


    public List<Employee> getEmployeeByDeptId(Integer deptId)
    {
        List<Employee> all = employeeRepository.findAll();
        all.stream().filter(e-> e.getDepartment().getId()==(deptId)).collect(Collectors.toList());
        return all;
    }
   public Boolean updateEmpDepartment(Integer emp_id , Integer dept_id) {
        Employee employee = employeeRepository.getById(emp_id);
        if(employee == null) {
            throw new GraphQLException("Employee with id "+emp_id+" not found");
        }

        Department department = departmentRepository.getById(dept_id);
        if(department == null) {
            throw new GraphQLException("Department with id "+dept_id+" not found");
        }

        employee.setDepartment(department);
        employeeRepository.save(employee);

        return true;
    }
}
