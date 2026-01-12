package com.ofbusiness.Employee.Repository;

import com.ofbusiness.Employee.Search.DepartmentDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DepartmentSearchRepository
        extends ElasticsearchRepository<DepartmentDocument, String> {
}
