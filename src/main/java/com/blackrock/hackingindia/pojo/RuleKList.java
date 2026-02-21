package com.blackrock.hackingindia.pojo;

import java.util.List;

import jakarta.validation.Valid;

public class RuleKList {
    
    //@NotEmpty(message = "RuleK list must not be empty")
    @Valid
    private List<RuleK> k;

    public RuleKList() {}

    public RuleKList(List<RuleK> k) {
        this.k = k;
    }

    public List<RuleK> getK() {
        return k;
    }

    public void setK(List<RuleK> k) {
        this.k = k;
    }
}
