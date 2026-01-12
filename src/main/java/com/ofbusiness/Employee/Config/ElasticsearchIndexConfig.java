package com.ofbusiness.Employee.Config;

import com.ofbusiness.Employee.Search.DepartmentDocument;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;

@Configuration
public class ElasticsearchIndexConfig {

    private final ElasticsearchOperations elasticsearchOperations;

    public ElasticsearchIndexConfig(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void createIndexIfNotExists() {

        IndexOperations indexOps =
                elasticsearchOperations.indexOps(DepartmentDocument.class);

        if (!indexOps.exists()) {
            indexOps.create();
            indexOps.putMapping(indexOps.createMapping());
        }
    }
}
