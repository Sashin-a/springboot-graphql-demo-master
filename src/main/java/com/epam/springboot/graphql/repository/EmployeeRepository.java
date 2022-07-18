package com.epam.springboot.graphql.repository;

import com.epam.springboot.graphql.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface  EmployeeRepository extends JpaRepository<Employee,Integer> {
}
