package com.greenstar.mecwheel.crb.model;

import com.greenstar.mecwheel.crb.controller.CRBFormActivity;

import java.util.List;

public class CRBData {
    private String name;
    private String code;

    List<CRBForm> crbForms;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<CRBForm> getCrbForms() {
        return crbForms;
    }

    public void setCrbForms(List<CRBForm> crbForms) {
        this.crbForms = crbForms;
    }
}
