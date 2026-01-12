package com.ofbusiness.Employee.DTO;

import java.io.Serializable;

public class EmployeeResponse implements Serializable {

    private Long eid;
    private String ename;
    private String email;
    private Long did;
    private String dname;


    public Long getEid() {
        return eid;
    }

    public void setEid(Long eid) {
        this.eid = eid;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDid() {
        return did;
    }

    public void setDid(Long did) {
        this.did = did;
    }

    public void setDname(String dname){this.dname = dname;}

    public  String getDname(){return dname;}
}
