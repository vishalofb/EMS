package com.ofbusiness.Employee.DTO;

import java.io.Serializable;

public class DepartmentResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String dname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }
}
