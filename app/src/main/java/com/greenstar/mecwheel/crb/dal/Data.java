package com.greenstar.mecwheel.crb.dal;



import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
    private String name;
    private String code;

    List<District> districts;
    List<DTCForm> dtcForms;

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

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public List<DTCForm> getDtcForms() {
        return dtcForms;
    }

    public void setDtcForms(List<DTCForm> dtcForms) {
        this.dtcForms = dtcForms;
    }
}
