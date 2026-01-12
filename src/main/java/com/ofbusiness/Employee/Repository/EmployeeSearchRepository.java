package com.ofbusiness.Employee.Repository;

import com.ofbusiness.Employee.Search.EmployeeDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EmployeeSearchRepository
        extends ElasticsearchRepository<EmployeeDocument, Long> {
}
