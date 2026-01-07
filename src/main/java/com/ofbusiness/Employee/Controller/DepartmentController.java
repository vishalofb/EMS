package com.ofbusiness.Employee.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ofbusiness.Employee.DTO.DepartmentRequest;
import com.ofbusiness.Employee.DTO.DepartmentResponse;
import com.ofbusiness.Employee.Services.DepartmentService;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // CREATE
    @PostMapping("/post-department")
    public DepartmentResponse createDepartment(
            @RequestBody DepartmentRequest request) {

        return departmentService.createDepartment(request);
    }

    // GET ALL
    @GetMapping("/get-departments")
    public List<DepartmentResponse> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    // GET BY ID
    @GetMapping("/get-departments/{id}")
    public DepartmentResponse getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }



    @PostMapping("/sync")
    public String syncData() {
        departmentService.syncAllData();
        return "Department data synced successfully!";
    }
}
