package com.ofbusiness.Employee.Controller;

import com.ofbusiness.Employee.DTO.EmployeeResponse;
import com.ofbusiness.Employee.Services.DepartmentSearchService;
import com.ofbusiness.Employee.Services.EmployeeSearchService;
import com.ofbusiness.Employee.Search.EmployeeDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search/employees")
public class EmployeeSearchController {

    @Autowired
    private EmployeeSearchService searchService;

    @Autowired
    private DepartmentSearchService departmentSearchService;

    @GetMapping("/name/{q}")
    public List<EmployeeDocument> searchEmployeeByName(
            @PathVariable String q) {

        return searchService.searchByNameFuzzy(q);
    }

    @GetMapping("/dname/{q}")
    public List<EmployeeResponse> searchEmployeesByDepartmentName(@PathVariable String q) {
//        System.out.println("tis is q "+q);
        return departmentSearchService.searchEmployeesByDepartmentName(q);
    }




}
