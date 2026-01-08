package com.ofbusiness.Employee.Services;

import com.ofbusiness.Employee.DTO.EmployeeResponse;
import com.ofbusiness.Employee.Search.DepartmentDocument;
import com.ofbusiness.Employee.Search.EmployeeDocument;
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

        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .should(s -> s.match(m -> m.field("dname").query(name)))
                                .should(s -> s.wildcard(w -> w.field("dname").value("*" + name.toLowerCase() + "*")))
                                .minimumShouldMatch("1")))
                .build();

        SearchHits<DepartmentDocument> hits = elasticsearchOperations.search(query, DepartmentDocument.class);

        return hits.getSearchHits().stream().map(hit -> hit.getContent()).collect(Collectors.toList());
    }

    // 🔍 Employees by department name
    public List<EmployeeResponse> searchEmployeesByDepartmentName(String deptName) {

        NativeQuery query = NativeQuery.builder()
                .withQuery(q -> q
                        .bool(b -> b
                                .should(s -> s.match(m -> m.field("dname").query(deptName)))
                                .should(s -> s.wildcard(w -> w.field("dname").value("*" + deptName.toLowerCase() + "*")))
                                .minimumShouldMatch("1")
                        )
                )
                .build();

        SearchHits<EmployeeDocument> hits =
                elasticsearchOperations.search(query, EmployeeDocument.class);

        if (hits.isEmpty()) {
            return List.of();
        }

        return hits.getSearchHits()
                .stream()
                .map(hit -> mapToEmployeeResponse(hit.getContent()))
                .toList();
    }


    private EmployeeResponse mapToEmployeeResponse(EmployeeDocument doc) {

        EmployeeResponse response = new EmployeeResponse();
        response.setEid(Long.valueOf(doc.getId()));
        response.setEname(doc.getEname());
        response.setEmail(doc.getEmail());
        response.setDname(doc.getDname());

        return response;
    }



}
