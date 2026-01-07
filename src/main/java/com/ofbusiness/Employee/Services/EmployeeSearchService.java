package com.ofbusiness.Employee.Services;

import com.ofbusiness.Employee.Search.EmployeeDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeSearchService {

        @Autowired
        private ElasticsearchOperations elasticsearchOperations;

        public List<EmployeeDocument> searchByNameFuzzy(String name) {

                NativeQuery query = NativeQuery.builder()
                                .withQuery(q -> q
                                                .match(m -> m
                                                                .field("ename")
                                                                .query(name)
                                                                .fuzziness("AUTO")))
                                .build();

                SearchHits<EmployeeDocument> hits = elasticsearchOperations.search(query, EmployeeDocument.class);

                return hits.getSearchHits()
                                .stream()
                                .map(hit -> hit.getContent())
                                .collect(Collectors.toList());
        }
}
