package com.ofbusiness.Employee.Repository;
import com.ofbusiness.Employee.Entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
   List<Employee> findByDepartment_Did(Long did);
   List<Employee> findByDepartment_Dname(String dname);
}
