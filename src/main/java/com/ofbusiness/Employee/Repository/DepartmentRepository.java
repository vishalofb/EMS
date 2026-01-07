package com.ofbusiness.Employee.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ofbusiness.Employee.Entity.Department;

public interface DepartmentRepository
        extends JpaRepository<Department, Long> {
}
