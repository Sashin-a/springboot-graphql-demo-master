package com.epam.springboot.graphql.repository;

import com.epam.springboot.graphql.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartmentRepository extends JpaRepository<Department,Integer> {
}
