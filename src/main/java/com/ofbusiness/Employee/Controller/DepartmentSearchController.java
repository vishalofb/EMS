package com.ofbusiness.Employee.Controller;

import com.ofbusiness.Employee.Search.DepartmentDocument;
import com.ofbusiness.Employee.Services.DepartmentSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search/departments")
public class DepartmentSearchController {

    @Autowired
    private DepartmentSearchService searchService;

    @GetMapping("/name/{q}")
    public List<DepartmentDocument> searchDepartmentByName(@PathVariable String q) {

//        System.out.println("this is q "+q);
        return searchService.searchByName(q);
    }



}
