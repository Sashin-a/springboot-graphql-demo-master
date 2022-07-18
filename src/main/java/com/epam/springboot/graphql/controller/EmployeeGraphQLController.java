package com.epam.springboot.graphql.controller;

import com.epam.springboot.graphql.entity.Department;
import com.epam.springboot.graphql.entity.SubmittedEmployee;
import com.epam.springboot.graphql.services.DepartmentService;
import com.epam.springboot.graphql.entity.Employee;
import com.epam.springboot.graphql.services.EmployeeService;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class EmployeeGraphQLController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentService departmentService;


    private FluxSink<Employee> employeeStream;
    private ConnectableFlux<Employee> employeePublisher;

    @PostConstruct
    public void init() {

        Flux<Employee> publisher = Flux.create(emitter -> {
            employeeStream = emitter;
        });

        employeePublisher = publisher.publish();
        employeePublisher.connect();

    }

    @SchemaMapping(typeName = "Query" , field = "employees") //RequestMapping
    public List<Employee>  getAllEmployees() {
        return employeeService.getAllEmployees();
    }


    @QueryMapping(value = "employee")
    public Employee getEmployeeById(@Argument Integer id) {
        return employeeService.getEmployeeById(id);
    }


    @QueryMapping(value="getEmployeesByDeptId")
    public List<Employee> getEmployeeByDeptId(@Argument Integer deptId){
        return employeeService.getEmployeeByDeptId(deptId);
    }



    @SchemaMapping(typeName = "Mutation" , field = "createEmployee")
    public Employee createEmployee(@Argument(name = "employee") SubmittedEmployee createEmpRequest) {
        Employee employee = new Employee();
        employee.setFirst_name(createEmpRequest.getFirst_name());
        employee.setLast_name(createEmpRequest.getLast_name());
        employee.setBirth_date(createEmpRequest.getBirth_date());
        employee.setGender(createEmpRequest.getGender());
        employee.setHire_date(createEmpRequest.getHire_date());

        if(createEmpRequest.getDeptId()!=null)
        {
            Department dept = departmentService.getDept(createEmpRequest.getDeptId());
            employee.setDepartment(dept);
        }
        employee = employeeService.createEmployee(employee);
       employeeStream.next(employee);

       return employee;
    }

    @MutationMapping //@PostMapping
    public boolean updateEmpDepartment(@Argument Integer emp_id ,@Argument Integer dept_id) {
        return employeeService.updateEmpDepartment(emp_id,dept_id);

    }

    @SubscriptionMapping
    public Publisher<Employee> notifyEmployeeCreation() {
        return employeePublisher;
    }


}
