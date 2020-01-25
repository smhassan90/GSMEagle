package com.greenstar.mecwheel.crb.model;

import java.util.List;

public class SyncObject {
    List<CRBForm> crbForms;

    List<DropdownCRBData> dropdownCRBData;

    public List<CRBForm> getCrbForms() {
        return crbForms;
    }

    public void setCrbForms(List<CRBForm> crbForms) {
        this.crbForms = crbForms;
    }

    public List<DropdownCRBData> getDropdownCRBData() {
        return dropdownCRBData;
    }

    public void setDropdownCRBData(List<DropdownCRBData> dropdownCRBData) {
        this.dropdownCRBData = dropdownCRBData;
    }
}
