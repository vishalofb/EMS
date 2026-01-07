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

    @PostMapping("/create")
    public EmployeeResponse createEmployee(@RequestBody EmployeeRequest request) {
        return employeeService.createEmployee(request);
    }


    @GetMapping("/eid/{eid}")
    public EmployeeResponse getEmployeeById(@PathVariable Long eid) {
        return employeeService.getEmployeeById(eid);
    }

    @GetMapping("/{did}")
    public List<EmployeeResponse> getEmployeeByDid(@PathVariable Long did) {
        return employeeService.getEmployeesByDid(did);
    }

    @GetMapping("/{dname}")
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
