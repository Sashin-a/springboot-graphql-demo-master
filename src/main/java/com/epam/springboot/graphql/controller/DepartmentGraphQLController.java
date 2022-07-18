package com.epam.springboot.graphql.controller;

import com.epam.springboot.graphql.services.DepartmentService;
import com.epam.springboot.graphql.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class DepartmentGraphQLController {

    @Autowired
    private DepartmentService departmentService;

    @SchemaMapping(typeName = "Query" , field = "departments")
    public List<Department>  getAllEmployees() {
        return departmentService.getAllDepartment();
    }



}
