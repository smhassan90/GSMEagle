package com.greenstar.eagle.dal;

import com.greenstar.eagle.model.CRForm;
import com.greenstar.eagle.model.Dashboard;
import com.greenstar.eagle.model.DropdownCRBData;

import java.io.Serializable;
import java.util.List;

public class EagleData implements Serializable {
    private String sitaraBajiName;
    private String sitaraBajiCode;
    private String AMName;
    private String AMCode;
    private String region;
    private String district;
    private String providerCode;
    private String providerName;
    private Dashboard dashboard;

    List<DropdownCRBData> dropdownCRBData;
    List<CRForm> crForms;

    public String getSitaraBajiName() {
        return sitaraBajiName;
    }

    public void setSitaraBajiName(String sitaraBajiName) {
        this.sitaraBajiName = sitaraBajiName;
    }

    public String getSitaraBajiCode() {
        return sitaraBajiCode;
    }

    public void setSitaraBajiCode(String sitaraBajiCode) {
        this.sitaraBajiCode = sitaraBajiCode;
    }

    public String getAMName() {
        return AMName;
    }

    public void setAMName(String AMName) {
        this.AMName = AMName;
    }

    public String getAMCode() {
        return AMCode;
    }

    public void setAMCode(String AMCode) {
        this.AMCode = AMCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public List<DropdownCRBData> getDropdownCRBData() {
        return dropdownCRBData;
    }

    public void setDropdownCRBData(List<DropdownCRBData> dropdownCRBData) {
        this.dropdownCRBData = dropdownCRBData;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public List<CRForm> getCrForms() {
        return crForms;
    }

    public void setCrForms(List<CRForm> crForms) {
        this.crForms = crForms;
    }
}