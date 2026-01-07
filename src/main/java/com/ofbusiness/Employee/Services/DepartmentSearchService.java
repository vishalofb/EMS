package com.ofbusiness.Employee.Services;

import com.ofbusiness.Employee.DTO.EmployeeResponse;
import com.ofbusiness.Employee.Search.DepartmentDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentSearchService {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private EmployeeService employeeService;

    // 🔍 Department name search
    public List<DepartmentDocument> searchByName(String name) {

        NativeQuery query = NativeQuery.builder().withQuery(q -> q.match(m -> m.field("dname").query(name))).build();

        SearchHits<DepartmentDocument> hits = elasticsearchOperations.search(query, DepartmentDocument.class);

        return hits.getSearchHits().stream().map(hit -> hit.getContent()).collect(Collectors.toList());
    }

    // 🔍 Employees by department name
    public List<EmployeeResponse> searchEmployeesByDepartmentName(String deptName) {

        NativeQuery query = NativeQuery.builder().withQuery(q -> q.match(m -> m.field("dname").query(deptName))).build();

        SearchHits<DepartmentDocument> hits = elasticsearchOperations.search(query, DepartmentDocument.class);

        if (hits.isEmpty()) {
            return List.of();
        }

        Long did = Long.valueOf(hits.getSearchHit(0).getContent().getId());
        return employeeService.getEmployeesByDid(did);
    }
}
