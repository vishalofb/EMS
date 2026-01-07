package com.ofbusiness.Employee.Search;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "department_index")
@Setting(settingPath = "/ElasticSearch/department-settings.json")
public class DepartmentDocument {

    @Id
    private String id;

    @Field(
            type = FieldType.Text,
            analyzer = "edge_ngram_analyzer",
            searchAnalyzer = "search_analyzer"
    )
    private String dname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }
}
