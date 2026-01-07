package com.ofbusiness.Employee.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ofbusiness.Employee.DTO.EmployeeRequest;
import com.ofbusiness.Employee.DTO.EmployeeResponse;
import com.ofbusiness.Employee.Services.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    // CREATE EMPLOYEE
    @PostMapping("/create")
    public EmployeeResponse createEmployee(@RequestBody EmployeeRequest request) {
        return employeeService.createEmployee(request);
    }

    // GET EMPLOYEE BY ID
    @GetMapping("id/{eid}")
    public EmployeeResponse getEmployeeById(@PathVariable Long eid) {
        return employeeService.getEmployeeById(eid);
    }

    @GetMapping("did/{did}")
    public List<EmployeeResponse> getEmployeeByDid(@PathVariable Long did) {
        return employeeService.getEmployeesByDid(did);
    }

    @GetMapping("/dname/{dname}")
    public List<EmployeeResponse> getEmployeesByDepartmentName(
            @PathVariable String dname) {

        return employeeService.getEmployeesByDepartmentName(dname);
    }


    @PostMapping("/sync")
    public String syncData() {
        employeeService.syncAllData();
        return "Data synced successfully!";
    }

}
