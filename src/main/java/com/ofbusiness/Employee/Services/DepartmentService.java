package com.ofbusiness.Employee.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.ofbusiness.Employee.DTO.DepartmentRequest;
import com.ofbusiness.Employee.DTO.DepartmentResponse;
import com.ofbusiness.Employee.Entity.Department;
import com.ofbusiness.Employee.Repository.DepartmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private com.ofbusiness.Employee.Repository.DepartmentSearchRepository departmentSearchRepository;

    // CREATE → evict cache
    @CacheEvict(value = { "departmentById", "allDepartments" }, allEntries = true)
    public DepartmentResponse createDepartment(DepartmentRequest request) {

        Department dept = new Department();
        dept.setDname(request.getDname());

        Department saved = departmentRepository.save(dept);

        // Sync to Elasticsearch
        try {
            com.ofbusiness.Employee.Search.DepartmentDocument document = new com.ofbusiness.Employee.Search.DepartmentDocument();
            document.setId(saved.getDid().toString());
            document.setDname(saved.getDname());
            departmentSearchRepository.save(document);
        } catch (Exception e) {
            System.err.println("Failed to sync department to Elasticsearch: " + e.getMessage());
            e.printStackTrace();
        }

        return mapToResponse(saved);
    }

    // GET BY ID → cached
    @Cacheable(value = "departmentById", key = "#id")
    public DepartmentResponse getDepartmentById(Long id) {

        Department dept = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        return mapToResponse(dept);
    }

    // GET ALL → cached
    @Cacheable(value = "allDepartments")
    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void syncAllData() {
        List<Department> allDepartments = departmentRepository.findAll();
        for (Department dept : allDepartments) {
            try {
                com.ofbusiness.Employee.Search.DepartmentDocument document = new com.ofbusiness.Employee.Search.DepartmentDocument();
                document.setId(dept.getDid().toString());
                document.setDname(dept.getDname());
                departmentSearchRepository.save(document);
            } catch (Exception e) {
                System.err.println("Failed to sync department " + dept.getDid() + ": " + e.getMessage());
            }
        }
    }

    // Entity → DTO
    private DepartmentResponse mapToResponse(Department dept) {
        DepartmentResponse response = new DepartmentResponse();
        response.setDid(dept.getDid()); // generated ID comes from DB
        response.setDname(dept.getDname());
        return response;
    }
}
