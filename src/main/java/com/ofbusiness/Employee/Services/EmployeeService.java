package com.ofbusiness.Employee.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.ofbusiness.Employee.DTO.EmployeeRequest;
import com.ofbusiness.Employee.DTO.EmployeeResponse;
import com.ofbusiness.Employee.Entity.Employee;
import com.ofbusiness.Employee.Entity.Department;
import com.ofbusiness.Employee.Repository.EmployeeRepository;
import com.ofbusiness.Employee.Repository.DepartmentRepository;

import java.util.List;
import java.util.ArrayList;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private com.ofbusiness.Employee.Repository.EmployeeSearchRepository employeeSearchRepository;

    // CREATE EMPLOYEE → clear related caches
    @CacheEvict(value = { "employeeById", "employeesByDept" }, allEntries = true)
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        Department department = departmentRepository.findById(request.getDid())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Employee employee = new Employee();
        employee.setEname(request.getEname());
        employee.setEmail(request.getEmail());
        employee.setDepartment(department);

        Employee savedEmployee = employeeRepository.save(employee);

        // Sync to Elasticsearch
        try {
            com.ofbusiness.Employee.Search.EmployeeDocument document = new com.ofbusiness.Employee.Search.EmployeeDocument();
            document.setId(savedEmployee.getEid().toString());
            document.setEname(savedEmployee.getEname());
            document.setEmail(savedEmployee.getEmail());
            document.setDname(savedEmployee.getDepartment().getDname());
            employeeSearchRepository.save(document);
        } catch (Exception e) {
            System.err.println("Failed to sync to Elasticsearch: " + e.getMessage());
            e.printStackTrace();
        }

        return mapToResponse(savedEmployee);
    }

    @Cacheable(value = "employeeById", key = "#eid")
    public EmployeeResponse getEmployeeById(Long eid) {

        System.out.println("Fetching employee from DB for id: " + eid);

        Employee employee = employeeRepository.findById(eid)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return mapToResponse(employee);
    }

        @Cacheable(value = "employeesByDept", key = "#did")
        public List<EmployeeResponse> getEmployeesByDid(Long did) {

            List<Employee> employees = employeeRepository.findByDepartment_Did(did);
            List<EmployeeResponse> responseList = new ArrayList<>();

            for (Employee employee : employees) {
                responseList.add(mapToResponse(employee));
            }

            return responseList;
        }

    @Cacheable(value = "employeesByDeptName", key = "#dname")
    public List<EmployeeResponse> getEmployeesByDepartmentName(String dname) {

        System.out.println("Fetching employees from DB for department name: " + dname);

        List<Employee> employees =
                employeeRepository.findByDepartment_Dname(dname);

        List<EmployeeResponse> responseList = new ArrayList<>();

        for (Employee employee : employees) {
            responseList.add(mapToResponse(employee));
        }

        return responseList;
    }


    public void syncAllData() {
        List<Employee> allEmployees = employeeRepository.findAll();
        for (Employee employee : allEmployees) {
            try {
                com.ofbusiness.Employee.Search.EmployeeDocument document = new com.ofbusiness.Employee.Search.EmployeeDocument();
                document.setId(employee.getEid().toString());
                document.setEname(employee.getEname());
                document.setEmail(employee.getEmail());
                document.setDname(employee.getDepartment().getDname());
                employeeSearchRepository.save(document);
            } catch (Exception e) {
                System.err.println("Failed to sync employee " + employee.getEid() + ": " + e.getMessage());
            }
        }
    }

    // ENTITY → DTO mapping
    private EmployeeResponse mapToResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setEid(employee.getEid());
        response.setEname(employee.getEname());
        response.setEmail(employee.getEmail());
        response.setDid(employee.getDepartment().getDid());
        return response;
    }
}
