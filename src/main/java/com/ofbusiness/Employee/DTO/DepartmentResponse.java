package com.ofbusiness.Employee.DTO;

import java.io.Serializable;

public class DepartmentResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long did;
    private String dname;

    public Long getDid() {
        return did;
    }

    public void setDid(Long did) {
        this.did = did;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }
}
