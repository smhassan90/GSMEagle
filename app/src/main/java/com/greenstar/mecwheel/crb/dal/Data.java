package com.greenstar.mecwheel.crb.dal;



import com.greenstar.mecwheel.crb.model.CRBForm;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
    private String name;
    private String code;

    List<CRBForm> forms;

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
}
