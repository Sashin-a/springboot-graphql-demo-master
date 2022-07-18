package com.epam.springboot.graphql.services;

import com.epam.springboot.graphql.entity.Department;
import com.epam.springboot.graphql.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;


    public List<Department> getAllDepartment()
    {
        return departmentRepository.findAll();
    }

    public Department getDept(Integer id)
    {
        return departmentRepository.findById(id).get();
    }
}
