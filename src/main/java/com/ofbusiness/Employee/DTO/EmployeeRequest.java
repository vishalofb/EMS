package com.ofbusiness.Employee.DTO;

public class EmployeeRequest {

    private String ename;
    private String email;
    private Long did;

    // getters & setters
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
}
